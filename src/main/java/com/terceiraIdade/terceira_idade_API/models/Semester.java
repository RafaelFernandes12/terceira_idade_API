 package com.terceiraIdade.terceira_idade_API.models;

  import java.time.LocalDateTime;
  import java.util.Objects;

  import jakarta.persistence.Column;
  import jakarta.persistence.Entity;
  import jakarta.persistence.Id;
  import jakarta.persistence.PrePersist;
  import lombok.Data;
  import lombok.NoArgsConstructor;

  @Entity
  @Data
  @NoArgsConstructor
  public class Semester {

    @Id
    @Column(unique = true)
    private Double year;

    private LocalDateTime start = LocalDateTime.now();

    private LocalDateTime end;

    @PrePersist
    public void prePersist() {
      if (end == null) {
        end = start.plusMonths(6);
      }
    }

    public Semester(Double year, LocalDateTime start, LocalDateTime end) {
      super();
      this.year = year;
      this.start = start;
      this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Semester other = (Semester) obj;
		return Objects.equals(year, other.year);
	}

	@Override
	public int hashCode() {
		return Objects.hash(year);
	}
}
