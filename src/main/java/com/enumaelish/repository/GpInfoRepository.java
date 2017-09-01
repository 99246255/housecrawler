package com.enumaelish.repository;

import com.enumaelish.entity.GPInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpInfoRepository extends CrudRepository<GPInfo, Long>, JpaSpecificationExecutor<GPInfo> {
}
