package io.leopard.web.mvc.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ImageListSerializer extends AbstractJsonSerializer<List<String>> {

	// ["/test/DSC_7348.jpg","/test/DSC_7352.jpg","/test/DSC_7353.jpg"]
	@Autowired
	private ImageUrlConverter imageUrlConverter;

	@Override
	public void out(List<String> list, JsonGenerator jgen, SerializerProvider provider) throws Exception {
		List<String> urlList = null;
		if (list != null) {
			urlList = new ArrayList<String>();
			for (String uri : list) {
				String url = imageUrlConverter.convert(uri);
				if (url != null) {
					urlList.add(url);
				}
			}
		}
		jgen.writeObject(urlList);
	}

}
