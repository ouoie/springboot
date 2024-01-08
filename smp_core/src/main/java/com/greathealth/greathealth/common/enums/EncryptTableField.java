package com.greathealth.greathealth.common.enums;

import com.greathealth.greathealth.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjm
 * @description: 需要加密的数据库表字段
 * @date 2022/10/09
 * @time 15:15
 */
@Getter
@AllArgsConstructor
public enum EncryptTableField {

    /**
     * h5用户名称
     */
    CUS_USER_NAME("cus_user_info","name"),

    /**
     * h5用户证件号
     */
    CUS_USER_CARD_NUM("cus_user_info","card_no"),

    /**
     * h5用户手机号
     */
    CUS_USER_PHONE("cus_user_info","phone_no"),

    /**
     * 护士证件号
     */
    NUR_USER_CARD_NUM("hos_nurse_info","card_no"),

    /**
     * 护士手机号
     */
    NUR_USER_PHONE("hos_nurse_info","card_no")
    ;

    private final String table;

    private final String field;

    public static List<String> getFieldsByTable(String table){
        ArrayList<String> fields = new ArrayList<>();
        for (EncryptTableField value : EncryptTableField.values()) {
            if(StringUtils.equalsAnyIgnoreCase(value.getTable(),table)){
                fields.add(value.getField());
            }
        }
        return fields;
    }

}
