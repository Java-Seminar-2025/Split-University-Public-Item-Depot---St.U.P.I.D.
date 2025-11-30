INSERT INTO universitys (universityName) VALUES
                                             ('Sveučilište u Splitu'),
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

INSERT INTO courses (courseName, universityId)
SELECT 'Računovodstvo i revizija', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Financije', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Marketing', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Menadžment', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Poslovna ekonomija', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Turizam', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Trgovina', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Entrepreneurship', Id FROM universitys WHERE universityName = 'Ekonomski fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Hrvatski jezik i književnost', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Engleski jezik i književnost', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Talijanski jezik i književnost', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Njemački jezik i književnost', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Povijest', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Filozofija', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Sociologija', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Pedagogija', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Psihologija', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Hrvatska kultura', Id FROM universitys WHERE universityName = 'Filozofski fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Arhitektura', Id FROM universitys WHERE universityName = 'Građevinsko-arhitektonski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Građevinarstvo', Id FROM universitys WHERE universityName = 'Građevinsko-arhitektonski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Geodezija i geoinformatika', Id FROM universitys WHERE universityName = 'Građevinsko-arhitektonski fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Kemija', Id FROM universitys WHERE universityName = 'Kemijsko-tehnološki fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Kemijsko inženjerstvo', Id FROM universitys WHERE universityName = 'Kemijsko-tehnološki fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Ekoinženjerstvo', Id FROM universitys WHERE universityName = 'Kemijsko-tehnološki fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Primijenjena kemija', Id FROM universitys WHERE universityName = 'Kemijsko-tehnološki fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Prehrambena tehnologija', Id FROM universitys WHERE universityName = 'Kemijsko-tehnološki fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Kineziologija', Id FROM universitys WHERE universityName = 'Kineziološki fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Sportski menadžment', Id FROM universitys WHERE universityName = 'Kineziološki fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Trener', Id FROM universitys WHERE universityName = 'Kineziološki fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Medicina', Id FROM universitys WHERE universityName = 'Medicinski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Dentalna medicina', Id FROM universitys WHERE universityName = 'Medicinski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Farmacija', Id FROM universitys WHERE universityName = 'Medicinski fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Brodostrojarstvo', Id FROM universitys WHERE universityName = 'Pomorski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Nautika', Id FROM universitys WHERE universityName = 'Pomorski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Pomorski menadžment', Id FROM universitys WHERE universityName = 'Pomorski fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Elektrotehnika i informacijska tehnologija', Id FROM universitys WHERE universityName = 'Pomorski fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Pravo', Id FROM universitys WHERE universityName = 'Pravni fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Javna uprava', Id FROM universitys WHERE universityName = 'Pravni fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Socijalni rad', Id FROM universitys WHERE universityName = 'Pravni fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Matematika', Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Fizika', Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Biologija', Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Kemija', Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Informatika', Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Računarstvo', Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Ekologija', Id FROM universitys WHERE universityName = 'Prirodoslovno-matematički fakultet Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Forenzika', Id FROM universitys WHERE universityName = 'Sveučilišni odjel za forenzične znanosti Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Kriminalistika', Id FROM universitys WHERE universityName = 'Sveučilišni odjel za forenzične znanosti Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Računarstvo', Id FROM universitys WHERE universityName = 'Sveučilišni odjel za stručne studije Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Mehatronika', Id FROM universitys WHERE universityName = 'Sveučilišni odjel za stručne studije Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Elektrotehnika', Id FROM universitys WHERE universityName = 'Sveučilišni odjel za stručne studije Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Upravljanje kvalitetom', Id FROM universitys WHERE universityName = 'Sveučilišni odjel za stručne studije Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Sestrinstvo', Id FROM universitys WHERE universityName = 'Sveučilišni odjel zdravstvenih studija Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Fizioterapija', Id FROM universitys WHERE universityName = 'Sveučilišni odjel zdravstvenih studija Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Radiološka tehnologija', Id FROM universitys WHERE universityName = 'Sveučilišni odjel zdravstvenih studija Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Sanitarno inženjerstvo', Id FROM universitys WHERE universityName = 'Sveučilišni odjel zdravstvenih studija Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Primalje', Id FROM universitys WHERE universityName = 'Sveučilišni odjel zdravstvenih studija Split';

INSERT INTO courses (courseName, universityId)
SELECT 'Teologija', Id FROM universitys WHERE universityName = 'Katolički bogoslovni fakultet Split';
INSERT INTO courses (courseName, universityId)
SELECT 'Katehetika', Id FROM universitys WHERE universityName = 'Katolički bogoslovni fakultet Split';