package csv.comisaria.com.util;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class Link {

	private String url;
	private String rel;
	
}
