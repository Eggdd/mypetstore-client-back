package org.csu.mypetstoreclient.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.mypetstoreclient.entity.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMapper extends BaseMapper<Cart> {
}
