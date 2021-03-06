package com.irdeto.jumpstart.workflow;

import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.drools.core.util.StringUtils;

import com.irdeto.jumpstart.domain.VideoSubcontent.FrameRate;

public class NotRequiredFrameRateDeserializer extends JsonDeserializer<FrameRate> {

	@Override
	public FrameRate deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		FrameRate enumValue = null;
		ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);
        Iterator<JsonNode> elements = node.getElements();
        while (elements.hasNext()) {
            String elementValue = (String) elements.next().getTextValue();
            if (!StringUtils.isEmpty(elementValue)) {
            	enumValue = FrameRate.fromValue(elementValue);
            }
        }
		return enumValue;
	}

}
