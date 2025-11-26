INSERT INTO universitys (universityName) VALUES ('Sveučilište u Splitu');

DECLARE @UniverzitetSplitId BIGINT;
SELECT @UniverzitetSplitId = Id FROM universitys WHERE universityName = 'Sveučilište u Splitu';

INSERT INTO universitys (universityName) VALUES
                                             ('Ekonomski fakultet Split'),
                                             ('Filozofski fakultet Split'),
                                             ('Građevinsko-arhitektonski fakultet Split'),
                                             ('Kemijsko-tehnološki fakultet Split'),
                                             ('Kineziološki fakultet Split'),
                                             ('Medicinski fakultet Split'),
                                             ('Pomorski fakultet Split'),
                                             ('Pravni fakultet Split'),
                                             ('Prirodoslovno-matematički fakultet Split'),
                                             ('Sveučilišni odjel za forenzične znanosti Split'),
                                             ('Sveučilišni odjel za stručne studije Split'),
                                             ('Sveučilišni odjel zdravstvenih studija Split'),
                                             ('Katolički bogoslovni fakultet Split');

DECLARE @EkonomskiId BIGINT, @FilozofskiId BIGINT, @GradjevinarskiId BIGINT,
        @KemijskiId BIGINT, @KineziologijskiId BIGINT, @MedicinskiId BIGINT,
        @PomorskiId BIGINT, @PravniId BIGINT, @PMFId BIGINT,
        @ForenzickiId BIGINT, @StrucniId BIGINT, @ZdravstveniId BIGINT, @BogoslovniId BIGINT;

SELECT @EkonomskiId = Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
SELECT @FilozofskiId = Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
SELECT @GradjevinarskiId = Id FROM universitys WHERE universityName = 'Građevinsko-arhitektonski fakultet Split';
SELECT @KemijskiId = Id FROM universitys WHERE universityName = 'Kemijsko-tehnološki fakultet Split';
SELECT @KineziologijskiId = Id FROM universitys WHERE universityName = 'Kineziološki fakultet Split';
SELECT @MedicinskiId = Id FROM universitys WHERE universityName = 'Medicinski fakultet Split';
SELECT @PomorskiId = Id FROM universitys WHERE universityName = 'Pomorski fakultet Split';
SELECT @PravniId = Id FROM universitys WHERE universityName = 'Pravni fakultet Split';
SELECT @PMFId = Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';
SELECT @ForenzickiId = Id FROM universitys WHERE universityName = 'Sveučilišni odjel za forenzične znanosti Split';
SELECT @StrucniId = Id FROM universitys WHERE universityName = 'Sveučilišni odjel za stručne studije Split';
SELECT @ZdravstveniId = Id FROM universitys WHERE universityName = 'Sveučilišni odjel zdravstvenih studija Split';
SELECT @BogoslovniId = Id FROM universitys WHERE universityName = 'Katolički bogoslovni fakultet Split';

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Računovodstvo i revizija', @EkonomskiId),
                                                   ('Financije', @EkonomskiId),
                                                   ('Marketing', @EkonomskiId),
                                                   ('Menadžment', @EkonomskiId),
                                                   ('Poslovna ekonomija', @EkonomskiId),
                                                   ('Turizam', @EkonomskiId),
                                                   ('Trgovina', @EkonomskiId),
                                                   ('Entrepreneurship', @EkonomskiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Hrvatski jezik i književnost', @FilozofskiId),
                                                   ('Engleski jezik i književnost', @FilozofskiId),
                                                   ('Talijanski jezik i književnost', @FilozofskiId),
                                                   ('Njemački jezik i književnost', @FilozofskiId),
                                                   ('Povijest', @FilozofskiId),
                                                   ('Filozofija', @FilozofskiId),
                                                   ('Sociologija', @FilozofskiId),
                                                   ('Pedagogija', @FilozofskiId),
                                                   ('Psihologija', @FilozofskiId),
                                                   ('Hrvatska kultura', @FilozofskiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Arhitektura', @GradjevinarskiId),
                                                   ('Građevinarstvo', @GradjevinarskiId),
                                                   ('Geodezija i geoinformatika', @GradjevinarskiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Kemija', @KemijskiId),
                                                   ('Kemijsko inženjerstvo', @KemijskiId),
                                                   ('Ekoinženjerstvo', @KemijskiId),
                                                   ('Primijenjena kemija', @KemijskiId),
                                                   ('Prehrambena tehnologija', @KemijskiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Kineziologija', @KineziologijskiId),
                                                   ('Sportski menadžment', @KineziologijskiId),
                                                   ('Trener', @KineziologijskiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Medicina', @MedicinskiId),
                                                   ('Dentalna medicina', @MedicinskiId),
                                                   ('Farmacija', @MedicinskiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Brodostrojarstvo', @PomorskiId),
                                                   ('Nautika', @PomorskiId),
                                                   ('Pomorski menadžment', @PomorskiId),
                                                   ('Elektrotehnika i informacijska tehnologija', @PomorskiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Pravo', @PravniId),
                                                   ('Javna uprava', @PravniId),
                                                   ('Socijalni rad', @PravniId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Matematika', @PMFId),
                                                   ('Fizika', @PMFId),
                                                   ('Biologija', @PMFId),
                                                   ('Kemija', @PMFId),
                                                   ('Informatika', @PMFId),
                                                   ('Računarstvo', @PMFId),
                                                   ('Ekologija', @PMFId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Forenzika', @ForenzickiId),
                                                   ('Kriminalistika', @ForenzickiId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Računarstvo', @StrucniId),
                                                   ('Mehatronika', @StrucniId),
                                                   ('Elektrotehnika', @StrucniId),
                                                   ('Upravljanje kvalitetom', @StrucniId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Sestrinstvo', @ZdravstveniId),
                                                   ('Fizioterapija', @ZdravstveniId),
                                                   ('Radiološka tehnologija', @ZdravstveniId),
                                                   ('Sanitarno inženjerstvo', @ZdravstveniId),
                                                   ('Primalje', @ZdravstveniId);

INSERT INTO courses (courseName, universityId) VALUES
                                                   ('Teologija', @BogoslovniId),
                                                   ('Katehetika', @BogoslovniId);