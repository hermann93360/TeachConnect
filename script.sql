CREATE TABLE schools (
    schoolId BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    name VARCHAR(255),
    address VARCHAR(255),
    contact VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(userId)
);

CREATE TABLE teachers (
    teacherId BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    specialty VARCHAR(255),
    skills VARCHAR(255),
    graduate VARCHAR(255),
    interest VARCHAR(255),
    wishLevel VARCHAR(255),
    website VARCHAR(255),
    availability VARCHAR(255),
    wishContractType VARCHAR(255),
    otherDataAboutTeacher VARCHAR(255),
    references VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(userId)
);

CREATE TABLE t_users (
    userId BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255),
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    password VARCHAR(255),
    phone VARCHAR(255),
    role VARCHAR(50),
    created_by BIGINT,
    FOREIGN KEY (created_by) REFERENCES users(userId)
);

CREATE TABLE applications (
    applicationId BIGINT AUTO_INCREMENT PRIMARY KEY,
    offer_id BIGINT,
    teacher_id BIGINT,
    applicationDate DATE,
    status VARCHAR(50),
    FOREIGN KEY (offer_id) REFERENCES joboffers(offerId),
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacherId)
);

CREATE TABLE joboffers (
    offerId BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_id BIGINT,
    description TEXT,
    publicationDate DATE,
    modificationDate DATE,
    status VARCHAR(50),
    skills VARCHAR(255),
    constraint VARCHAR(255),
    period VARCHAR(255),
    notes TEXT,
    FOREIGN KEY (school_id) REFERENCES schools(schoolId)
);

INSERT INTO t_users (login, prenom, nom, motDePasse, telephone, role) VALUES
('adminEcole1@gmail.com', 'Alice', 'Dupont', 'motdepasse1', '0102030405', 'SCHOOL'),
('prof1@gmail.com', 'Jean', 'Durand', 'motdepasse2', '0607080910', 'TEACHER'),
('prof2@gmail.com', 'Marie', 'Leroux', 'motdepasse3', '0611223344', 'TEACHER');

INSERT INTO schools (id_utilisateur, nom, adresse, contact) VALUES
(1, 'Lycée Montaigne', '123 Rue Principale', '01 02 03 04 05'),
(1, 'Collège Voltaire', '456 Avenue de la Liberté', '02 03 04 05 06');

-- Insérer des Professeurs
INSERT INTO teachers (id_utilisateur, specialite, competences, diplome, interets, niveauSouhaite, siteWeb, disponibilite, typeContratSouhaite, autresInfos, references) VALUES
(2, 'Mathématiques', 'Algèbre, Géométrie', 'Master en Mathématiques', 'Enseignement, Recherche', 'Lycée', 'http://www.prof-maths.com', 'Plein temps', 'CDI', 'Expérience de 5 ans en enseignement', 'Référence1, Référence2'),
(3, 'Sciences', 'Biologie, Chimie', 'Doctorat en Biologie', 'Laboratoire, Terrain', 'Collège', 'http://www.prof-sciences.com', 'Mi-temps', 'CDD', 'Expérience en laboratoire', 'Référence3, Référence4');

-- Insérer des Offres d'emploi
INSERT INTO jobOffers (id_ecole, description, datePublication, dateModification, statut, competences, contraintes, periode, notes) VALUES
(1, 'Recherche professeur de mathématiques', '2023-01-01', '2023-01-10', 'OUVERT', 'Analyse, Probabilités', 'Disponibilité immédiate', 'Année scolaire 2023-2024', 'Poste basé à Paris'),
(2, 'Professeur de sciences pour le collège', '2023-02-01', '2023-02-10', 'OUVERT', 'Biologie générale', 'Expérience avec adolescents', 'Année scolaire 2023-2024', 'Poste basé à Lyon');

-- Insérer des Candidatures
INSERT INTO applications (id_offre, id_professeur, dateCandidature, statut) VALUES
(1, 2, '2023-01-15', 'EN_ATTENTE'),
(2, 3, '2023-02-15', 'EN_ATTENTE');