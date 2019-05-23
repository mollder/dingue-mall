package org.ingue.mall.config.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

/**
 * Controller에서 받는 Errors 객체는
 * 자바 Bean 스펙을 따르지 않아서 BeanSerializer를 이용하지 못함
 *
 * 그래서 JSON으로 변환하기 위해서 Errors객체를 위한 Serializer를 설정해주어야 함
 *
 * 아래 클래스가 Errors 객체를 위한 Serializer
 */
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {
    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        errors.getFieldErrors().forEach(e -> {
            try {
                gen.writeStartObject();

                gen.writeStringField("field", e.getField());
                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());

                Object rejectedValue = e.getRejectedValue();

                if(rejectedValue != null) {
                    gen.writeStringField("rejectedValue", rejectedValue.toString());
                }

                gen.writeEndObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        errors.getGlobalErrors().forEach(e -> {
            try {
                gen.writeStartObject();

                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());

                gen.writeEndObject();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        gen.writeEndArray();
    }
}
