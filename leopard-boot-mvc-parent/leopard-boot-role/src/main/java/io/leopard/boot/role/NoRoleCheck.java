package io.leopard.boot.role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 不做角色检查
 * 
 * @author 谭海潮
 *
 */
@Target({ ElementType.METHOD })
public @interface NoRoleCheck {

}
