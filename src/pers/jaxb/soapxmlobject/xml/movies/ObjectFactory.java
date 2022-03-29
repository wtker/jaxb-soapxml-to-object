package pers.jaxb.soapxmlobject.xml.movies;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {}
	
	public MoviesResponse createMoviesResponse() {
		return new MoviesResponse();
	}
	
	public MoviesParamDtls createMoviesParamDtls() {
		return new MoviesParamDtls();
	}
}
