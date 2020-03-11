package com.wow.security.validator;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.wow.security.request.UserData;



@Component
public class CustomValidator implements Validator {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	/**
	 * Validate.
	 *
	 * @param unrgstdDealerRgstnRequest the unrgstd dealer rgstn request
	 * @param errors the errors
	 * @param allowNull the allow null
	 */
	public void validate(UserData userData, Errors errors, Boolean allowNull) {

		try {
			if (userData != null) {
				if (!ESAPI.validator().isValidInput("dob",
						userData.getDob(), "Date", 10, allowNull)) {
					if (Boolean.FALSE.equals(allowNull)) {
						if (StringUtils.isBlank(userData.getDob()))
							errors.rejectValue("dob", "dob",
									"Field cannot be left blank.");
						else
							errors.rejectValue("dob", "dob",
									"Invalid check date (Correct format: dd/MM/yyyy)");
					} else {
						errors.rejectValue("dob", "dob",
								"Invalid check date (Correct format: dd/MM/yyyy)");
					}
				}
				
				if(!ESAPI.validator().isValidInput("name",
						userData.getName(), "Name", 10, allowNull)){
					if (Boolean.FALSE.equals(allowNull)) {
						if (StringUtils.isBlank(userData.getName()))
							errors.rejectValue("name", "name",
									"Field cannot be left blank.");
						else
							errors.rejectValue("name", "name",
									"Invalid name");
					} else {
						errors.rejectValue("name", "name",
								"Invalid name");
					}
				}
			} else {
				errors.rejectValue("ewayBillStatusRequest", "ewayBillStatusRequest",
						"Invalid eway Bill Status Request");
			}
			
		} catch (Exception e) {
			LOGGER.error("Validation fails: {}", e);
		}

	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new UnsupportedOperationException();
	}

}
