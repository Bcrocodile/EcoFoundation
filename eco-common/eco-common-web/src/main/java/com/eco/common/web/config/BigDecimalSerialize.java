package com.eco.common.web.config;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author bcro
 */
public class BigDecimalSerialize extends StdSerializer<BigDecimal>{
        public final static BigDecimalSerialize INSTANCE = new BigDecimalSerialize();

        public BigDecimalSerialize() {
            super(BigDecimal.class);
        }
        @Override
        public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if(value == null){
                gen.writeString("0.00");
            }else{
                gen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
            }
        }
}
