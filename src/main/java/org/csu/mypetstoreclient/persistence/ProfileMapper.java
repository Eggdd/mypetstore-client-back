package org.csu.mypetstoreclient.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.mypetstoreclient.entity.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileMapper extends BaseMapper<Profile> {
}
