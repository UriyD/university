package com.degtyaruk.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.util.List;
import java.util.Objects;

@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Course> courses;

    @Column(name = "email")
    private String email;

    @Column(name = "parole")
    private String parole;

    public User() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setParole(String parole) {
        this.parole = parole;
    }

    protected User(UserBuilder<? extends UserBuilder> userBuilder) {
        this.id = userBuilder.id;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.courses = userBuilder.courses;
        this.email = userBuilder.email;
        this.parole = userBuilder.parole;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public String getEmail() {
        return email;
    }

    public String getParole() {
        return parole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User that = (User) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.courses, that.courses) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.parole, that.parole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, courses, email, parole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", parole='" + parole + '\'';
    }

    public static class UserBuilder<SELF extends UserBuilder<SELF>> {
        private Integer id;
        private String firstName;
        private String lastName;
        private List<Course> courses;
        private String email;
        private String parole;

        protected UserBuilder() {
        }

        @SuppressWarnings("unchecked")
        public SELF self() {
            return (SELF) this;
        }

        public SELF withId(Integer id) {
            this.id = id;
            return self();
        }

        public SELF withFirstName(String firstName) {
            this.firstName = firstName;
            return self();
        }

        public SELF withLastName(String lastName) {
            this.lastName = lastName;
            return self();
        }

        public SELF withCourses(List<Course> courses) {
            this.courses = courses;
            return self();
        }

        public SELF withEmail(String email) {
            this.email = email;
            return self();
        }

        public SELF withParole(String parole) {
            this.parole = parole;
            return self();
        }

        public User build() {
            return new User(self());
        }
    }
}

