package com.yrkim.yrkimapi.utils.entity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityConverter {

    private EntityManager entityManager;

    /**
     * Dto의 property를 복사하여 entity Class Type의 영속성 인스턴스를 반환합니다.
     * Dto가 Entity의 @Id property을 가지고 있다면 EntityManager를 통해 DB의 값을 가져오고
     * id prop이 없거나 해당 id에 맞는 Table row가 없다면 생성자를 이용하여 새로운 인스턴스를 생성합니다.
     * <br>
     * EntityManager를 이용하기 때문에 다른 트랜젝션 범위에서 return된 인스턴스를 사용하면 의도치 않은 작동을 일으킬 수 있습니다.
     *
     * @param dto Entity의 property를 가지고 있는 dto, 복사하고 싶은 prop는 Entity의 prop명과 동일해야합니다.
     * @param entityClass 반환할 entity의 Type, @Entity를 가지고 있는 영속성 Type이어야 합니다.
     * @param <E> Entity class
     * @param <D> DTO class
     * @return entityClass
     */
    public <E,D> E getEntity(D dto, Class<E> entityClass){

        if(entityClass.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException(entityClass.getName() + " this is not Entity");
        }

        String idGetterMethodName = getIdGetterMethodName(entityClass);

        // entity 인스턴스 생성
        E entity = null;
        try{
            Object pk = dto.getClass().getMethod(idGetterMethodName).invoke(dto);

            // DTO가 Id를 가지고 있다면 entity manager에서 가져옴
            // XXX getRefer || find ?
            entity = entityManager.find(entityClass, pk);

        } catch (NoSuchMethodException | IllegalAccessException | EntityNotFoundException | InvocationTargetException e) {

            log.debug("entity create by constructor", e);

            // DTO가 Id를 가지고 있지 않다면 생성자를 통해 생성
            try{
                entity = entityClass.getConstructor().newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException x){
                throw new IllegalArgumentException("entity(" + entityClass.getName() + ")의 객체를 생성 할 수 없습니다.", x);
            }
        }

        copyProperties(dto, entity);

        return entity;
    }

    private <T> String getIdFieldName(Class<T> entityClass){

        String idFieldName = null;

        for(Field field : entityClass.getDeclaredFields()){

            if(field.getAnnotation(EntityId.class) != null){
                idFieldName = field.getName();
            }
        }

        return idFieldName;
    }

    private <T> String getIdGetterMethodName(Class<T> entityClass){

        String idFieldName = getIdFieldName(entityClass);

        String idGetterName = null;
        if(idFieldName == null){
            Method[] methods = entityClass.getDeclaredMethods();

            for(Method method : methods){
                if(method.getAnnotation(EntityId.class) != null){
                    idGetterName = method.getName();
                }
            }
        } else {
            idGetterName = "get"+idFieldName.substring(0, 1).toUpperCase() + idFieldName.substring(1);
        }

        return idGetterName;
    }

    private void copyProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target);

        Field[] sourceFields = source.getClass().getFields(),
                targetFields = target.getClass().getFields();

    }

    public <D, E> D toDTO(E entity, Class<D> dtoClass){
        D dto = null;
        try{
            dto = dtoClass.getConstructor().newInstance();
            copyProperties(entity, dto);

        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e){
            throw new IllegalArgumentException(dtoClass.getName() + " type의 객체를 생성 할 수 없습니다.", e);
        }

        return dto;
    }

}
