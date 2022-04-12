package org.csu.mypetstoreclient.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.mypetstoreclient.entity.Supplier;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierMapper extends BaseMapper<Supplier> {
}
