
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Demoa entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "demoa", catalog = "focus")

public class Demoa implements java.io.Serializable {

	// Fields

	private Integer id;
	private String test1;
	private Timestamp test2;
	private String test3;
	private Set<Demob> demobs = new HashSet<Demob>(0);

	// Constructors

	/** default constructor */
	public Demoa() {
	}

	/** full constructor */
	public Demoa(String test1, Timestamp test2, String test3, Set<Demob> demobs) {
		this.test1 = test1;
		this.test2 = test2;
		this.test3 = test3;
		this.demobs = demobs;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "demoa")

	public Set<Demob> getDemobs() {
		return this.demobs;
	}

	public void setDemobs(Set<Demob> demobs) {
		this.demobs = demobs;
	}

}