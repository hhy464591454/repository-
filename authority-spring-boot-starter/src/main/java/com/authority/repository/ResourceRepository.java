package com.authority.repository;

import com.authority.bean.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource,String> {

    List<Resource> findResourceByIdInAndTypeEqualsOrderByLevelDescSortNumAsc(List<String> list, Short type);

    List<Resource> findResourceByTypeEqualsOrderByLevelDescSortNumAsc(Short type);

    List<Resource> findResourceByIdIn(List<String> list);

    List<Resource> findResourceByCodeContainingAndCodeNameContainingAndLevelInAndTypeIn(String code, String codeName, List<Short> levels, List<Short> types);

}
