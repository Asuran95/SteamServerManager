package steamservermanager.utils;

import org.modelmapper.ModelMapper;

public class ObjectUtils {
	
	private static ModelMapper mapper = new ModelMapper();

	public static <D> D copyObject(Object source, Class<D> destinationType) {
		return mapper.map(source, destinationType);
	}
}
