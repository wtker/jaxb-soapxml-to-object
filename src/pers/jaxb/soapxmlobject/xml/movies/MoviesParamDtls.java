package pers.jaxb.soapxmlobject.xml.movies;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoviesParamDtls", propOrder = {"movieName","movieType","publisher"})
public class MoviesParamDtls {

	@XmlElement(name = "MovieName")
	protected String movieName;
	
	@XmlElement(name = "MovieType")
	protected String movieType;
	
	@XmlElement(name = "Publisher")
	protected String publisher;

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieType() {
		return movieType;
	}

	public void setMovieType(String movieType) {
		this.movieType = movieType;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
