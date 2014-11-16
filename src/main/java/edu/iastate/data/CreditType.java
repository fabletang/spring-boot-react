package edu.iastate.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "creditType")
@XmlType(name = "CreditTypeType")
@XmlEnum
public enum CreditType {
    @XmlEnumValue("V") VARIABLE("V"),
    @XmlEnumValue("R") RSOMETHING("R"),
    /**
     *
     * Member type corresponding to KIM groups
     */
    @XmlEnumValue("F") FIXED("F");

    public final String code;

    private CreditType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static CreditType fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (CreditType creditType : values()) {
            if (creditType.code.equals(code)) {
                return creditType;
            }
        }
        throw new IllegalArgumentException("Failed to locate the CreditType with the given code: " + code);
    }
}
