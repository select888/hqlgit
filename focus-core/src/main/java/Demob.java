
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Demob entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "demob", catalog = "focus")

public class Demob implements java.io.Serializable {

	// Fields

	private Integer id;
	private Demoa demoa;
	private String test1;
	private Timestamp test2;
	private String test3;

	// Constructors

	/** default constructor */
	public Demob() {
	}

	/** full constructor */
	public Demob(Demoa demoa, String test1, Timestamp test2, String test3) {
		this.demoa = demoa;
		this.test1 = test1;
		this.test2 = test2;
		this.test3 = test3;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "demoaid")

	public Demoa getDemoa() {
		return this.demoa;
	}

	public void setDemoa(Demoa demoa) {
		this.demoa = demoa;
	}

	@Column(name = "test1", length = 111)

	public String getTest1() {
		return this.test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	@Column(name = "test2", length = 19)

	public Timestamp getTest2() {
		return this.test2;
	}

	public void setTest2(Timestamp test2) {
		this.test2 = test2;
	}

	@Column(name = "test3", length = 111)

	public String getTest3() {
		return this.test3;
	}

	public void setTest3(String test3) {
		this.test3 = test3;
	}

}