package org.csu.mypetstoreclient.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.mypetstoreclient.entity.Sequence;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper extends BaseMapper<Sequence> {
}
