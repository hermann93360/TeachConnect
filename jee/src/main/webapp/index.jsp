<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <style>
        form {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        input[type="radio"] {
            margin-right: 5px;
        }

        label {
            margin-right: 20px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="email"],
        input[type="tel"],
        input[type="url"],
        input[type="password"],
        textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="tel"]:focus,
        input[type="url"]:focus,
        input[type="password"]:focus,
        textarea:focus {
            border-color: #0056b3;
            outline: none;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin: 20px 0;
            border: none;
            border-radius: 4px;
            background-color: #0056b3;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #004494;
        }

        textarea {
            height: 100px;
        }

        input[placeholder], textarea[placeholder] {
            font-style: italic;
        }

        input[type="radio"] + label {
            display: inline-block;
            margin-bottom: 10px;
        }

        input[type="radio"]:checked + label {
            color: #0056b3;
        }
    </style>
</head>
<body>
<form action="TeacherServlet" method="post">
    <input type="radio" id="ecole" name="type" value="ecole">
    <label for="ecole">Ecole</label>

    <input type="radio" id="professeur" name="type" value="professeur" checked>
    <label for="professeur">Professeur</label>
    <input type="text" name="nom" placeholder="Nom" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="text" name="competences" placeholder="Compétences">
    <input type="tel" name="telephone" placeholder="Téléphone">
    <input type="text" name="diplome" placeholder="Diplômé">
    <input type="url" name="siteWeb" placeholder="Site web">
    <input type="text" name="centreInteret" placeholder="Centre d'intérêt">
    <input type="text" name="disponibilite" placeholder="Disponibilité">
    <input type="text" name="niveauSouhaite" placeholder="Niveau souhaité">
    <input type="text" name="typeContrat" placeholder="Type de contrat souhaité">
    <input type="password" name="motDePasse" placeholder="Mot de passe" required>
    <textarea name="autresInfos" placeholder="Autres informations sur l'enseignant"></textarea>
</form>
</body>
</html>