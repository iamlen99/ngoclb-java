create schema academy_db;

use academy_db;

create table admin
(
    id       int auto_increment primary key,
    username varchar(50)  not null unique,
    password varchar(255) not null
);

create table student
(
    id        int auto_increment primary key,
    name      varchar(100) not null,
    dob       date         not null,
    email     varchar(100) not null unique,
    sex       bit          not null,
    phone     varchar(20),
    password  varchar(255) not null,
    create_at date
);

create table course
(
    id         int auto_increment primary key,
    name       varchar(100) not null,
    duration   int          not null check ( duration > 0 ),
    instructor varchar(100) not null,
    create_at  date
);

create table enrollment
(
    id            int auto_increment primary key,
    student_id    int not null,
    course_id     int not null,
    registered_at datetime                                        default current_timestamp,
    status        enum ('WAITING', 'DENIED', 'CANCEL', 'CONFIRM') default 'WAITING',
    foreign key (student_id) references student (id) on delete cascade,
    foreign key (course_id) references course (id) on delete cascade
);

INSERT INTO admin (username, password)
VALUES ('admin1', 'admin123'),
       ('superadmin', 'securepass');

INSERT INTO student (name, dob, email, sex, phone, password)
VALUES ('Nguyen Van A', '2000-01-01', 'a.nguyen@example.com', 1, '0912345678', 'pass123'),
       ('Tran Thi B', '1999-05-20', 'b.tran@example.com', 0, '0987654321', 'abcxyz'),
       ('Le Van C', '2001-07-15', 'c.le@example.com', 1, '0909090909', 'qwerty');

INSERT INTO course (name, duration, instructor)
VALUES ('Java Programming', 60, 'Mr. Dinh'),
       ('Web Development', 45, 'Ms. Hoa'),
       ('Database Design', 30, 'Mr. Tuan');

INSERT INTO enrollment (student_id, course_id, status)
VALUES (1, 1, 'CONFIRM'),
       (1, 2, 'WAITING'),
       (2, 1, 'CANCEL'),
       (3, 3, 'CONFIRM');


# Tao procedure bang Course

DELIMITER $$
create procedure find_all_courses()
begin
    select * from course;
end $$
DELIMITER ;

DELIMITER $$
create procedure add_course(
    name_in varchar(100),
    duration_in int,
    instructor_in varchar(100)
)
begin
    insert into course (name, duration, instructor, create_at)
    values (name_in, duration_in, instructor_in, current_date);
end $$
DELIMITER ;

DELIMITER $$
create procedure update_course(
    id_in int,
    name_in varchar(100),
    duration_in int,
    instructor_in varchar(100)
)
begin
    update course
    set name       = name_in,
        duration   = duration_in,
        instructor = instructor_in
    where id = id_in;
end $$
DELIMITER ;

DELIMITER $$
create procedure find_course_by_id(
    id_in int
)
begin
    select * from course where id = id_in;
end $$
DELIMITER ;

DELIMITER $$
create procedure delete_course_by_id(
    id_in int
)
begin
    delete from course where id = id_in;
end $$
DELIMITER ;
# drop procedure search_course_by_name;
DELIMITER $$
create procedure search_course_by_name(
    name_in varchar(100)
)
begin
    declare search_value varchar(100);
    set search_value = concat('%', concat(name_in, '%'));
    select * from course where name like search_value;
end $$
DELIMITER ;

# Tao procedure bang Student

DELIMITER $$
create procedure find_all_students()
begin
    select * from student;
end $$
DELIMITER ;

DELIMITER $$
create procedure add_student(
    name_in varchar(100),
    dob_in date,
    email_in varchar(100),
    sex_in bit,
    phone_in varchar(20),
    password_in varchar(255)
)
begin
    insert into student (name, dob, email, sex, phone, password)
    values (name_in, dob_in, email_in, sex_in, phone_in, password_in);
end $$
DELIMITER ;

DELIMITER $$
create procedure update_student(
    id_in int,
    name_in varchar(100),
    dob_in date,
    email_in varchar(100),
    sex_in bit,
    phone_in varchar(20),
    password_in varchar(255)
)
begin
    update student
    set name     = name_in,
        dob      = dob_in,
        email    = email_in,
        sex      = sex_in,
        phone    = phone_in,
        password = password_in
    where id = id_in;
end $$
DELIMITER ;

DELIMITER $$
create procedure delete_student(
    id_in int
)
begin
    delete from student where id = id_in;
end $$
DELIMITER ;

DELIMITER $$
create procedure search_students(
    keyword_in varchar(100)
)
begin
    declare keyword varchar(100);
    set keyword = concat('%', keyword_in, '%');
    select *
    from student
    where name like keyword
       or email like keyword
       or cast(id as char) like keyword;
end $$
DELIMITER ;

# Tao procedure bang enrollment

DELIMITER $$
create procedure find_all_enrollments_by_student_id(
    id_in int
)
begin
    select e.id, e.student_id, e.course_id, c.name as course_name, e.registered_at, e.status
    from enrollment as e
             inner join course c on e.course_id = c.id
    where student_id = id_in;
end $$
DELIMITER ;

DELIMITER $$
create procedure add_enrollment(
    student_id_in int,
    course_id_in int
)
begin
    insert into enrollment (student_id, course_id)
    values (student_id_in, course_id_in);
end $$
DELIMITER ;

DELIMITER $$
create procedure cancel_enrollment(
    id_in int
)
begin
    update enrollment
    set status = 'CANCEL'
    where id = id_in;
end $$
DELIMITER ;

DELIMITER $$
create procedure find_enrollment_by_id(
    id_in int
)
begin
    select e.id, e.student_id, e.course_id, c.name as course_name, e.registered_at, e.status
    from enrollment as e
             inner join course c on e.course_id = c.id
    where e.id = id_in;
end $$
DELIMITER ;

DELIMITER $$
create procedure find_students_by_course()
begin
    select e.id, e.student_id, s.name as student_name, e.course_id, c.name as course_name, e.registered_at, e.status
    from enrollment as e
             inner join course c on e.course_id = c.id
             inner join student s on e.student_id = s.id;
end $$
DELIMITER ;

# Thong ke

DELIMITER $$
create procedure statistics_all_courses_and_all_students(
    out count_courses int,
    out count_students int
)
begin
    select count(id) into count_courses from course;
    select count(id) into count_students from student;
end $$
DELIMITER ;

DELIMITER $$
create procedure statistics_count_students_by_each_course()
begin
    select c.name as course_name, count(e.student_id)
    as count_students
    from enrollment e
             inner join course c on e.course_id = c.id
    group by e.course_id;
end $$
DELIMITER ;

DELIMITER $$
create procedure get_top_5_course_by_student_count()
begin
    select c.name as course_name, count(e.student_id)
                  as count_students
    from enrollment e
             inner join course c on e.course_id = c.id
    group by e.course_id
    order by count_students DESC
    limit 5;
end $$
DELIMITER ;
# drop procedure get_courses_have_more_than_10_student;
DELIMITER $$
create procedure get_courses_have_more_than_10_student()
begin
    select c.name as course_name, count(e.student_id)
                  as count_students
    from enrollment e
             inner join course c on e.course_id = c.id
    group by e.course_id
    having count(e.student_id) >10;
end $$
DELIMITER ;