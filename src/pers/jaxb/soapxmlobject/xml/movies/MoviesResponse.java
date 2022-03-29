package pers.jaxb.soapxmlobject.xml.movies;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"status","date","moviesParam"})
@XmlRootElement(name="MoviesResponse", namespace="")
public class MoviesResponse {

	@XmlElement(name = "Status")
	protected String status;
	
	@XmlElement(name = "Date")
	protected String date;
	
	@XmlElement(name = "MoviesParam")
	protected MoviesParamDtls moviesParam;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public MoviesParamDtls getMoviesParam() {
		return moviesParam;
	}

	public void setMoviesParam(MoviesParamDtls moviesParam) {
		this.moviesParam = moviesParam;
	}
}
