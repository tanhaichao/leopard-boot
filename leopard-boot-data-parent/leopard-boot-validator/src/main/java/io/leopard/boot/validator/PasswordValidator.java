package io.leopard.boot.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.leopard.boot.validator.constraints.Password;

/**
 * 密码合法性验证
 * 
 * @author 谭海潮
 *
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {
	private Password annotation;

	@Override
	public void initialize(Password annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		System.err.println("PasswordValidator password:" + password);
		return true;
	}

}
