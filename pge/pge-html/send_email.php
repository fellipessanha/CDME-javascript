<?php
$to1 = "conteudosdigitais@im.uff.br";
$to2 = "conteudos.digitais.uff@gmail.com";

$today = getdate(); 

$software = "Um Problema Geométrico";

$textoweb = $_POST['textoweb'];
$sugestoesweb = $_POST['sugestoesweb'];
$uso = $_POST['uso'];
$nota = $_POST['nota'];

$rigor = $_POST['rigor'];
$interatividade = $_POST['interatividade'];
$linguagem = $_POST['linguagem'];
$contextualizacao = $_POST['contextualizacao'];
$experimentacao = $_POST['experimentacao'];
$alternativas = $_POST['alternativas'];
$layout = $_POST['layout'];
$dicas = $_POST['dicas'];
$navegacao = $_POST['navegacao'];
$mundoreal = $_POST['mundoreal'];
$interdisciplinaridade = $_POST['interdisciplinaridade'];

$nome = $_POST['nome'];
$e_mail = $_POST['e_mail'];
$localidade = $_POST['localidade'];
$conexao = $_POST['conexao'];
$atuacao = $_POST['atuacao'];
$categoria = $_POST['categoria'];
$instituicao = $_POST['instituicao'];


$navigator = $_POST['navigator'];
$version = $_POST['version'];
$platform = $_POST['platform'];
$resolution = $_POST['resolution'];
$java = $_POST['java'];


$from_header = "From: \"$nome\" <$e_mail>";
$subject = "Avaliação do software ".$software." por ".$nome;

$contents = "Nome: ";
$contents .= $nome;
$contents .= "\n";
$contents .= "E-mail: ";
$contents .= $e_mail;
$contents .= "\n";
$contents .= "Localidade: ";
$contents .= $localidade;
$contents .= "\n";
$contents .= "Conexão: ";
$contents .= $conexao;
$contents .= "\n";
$contents .= "Atuação: ";
$contents .= $atuacao;
$contents .= "\n";
$contents .= "Categoria: ";
$contents .= $categoria;
$contents .= "\n";
$contents .= "Instituição: ";
$contents .= $instituicao;
$contents .= "\n\n\n";


$contents .= "Data: ";
$contents .= $today[mday];
$contents .= "/";
$contents .= $today[mon];
$contents .= "/";
$contents .= $today[year];
$contents .= "\n\n\n";


$contents .= "Navegador: ";
$contents .= $navigator;
$contents .= "\n";
$contents .= "Versão: ";
$contents .= $version;
$contents .= "\n";
$contents .= "Plataforma: ";
$contents .= $platform;
$contents .= "\n";
$contents .= "Resolução: ";
$contents .= $resolution;
$contents .= "\n";
$contents .= "Java: ";
$contents .= $java;
$contents .= "\n\n\n";

$contents .= "Você acha que o texto usado na página WEB está claro? Em caso negativo, que modificações você acha que são necessárias?\n";
$contents .= $textoweb;
$contents .= "\n\n";

$contents .= "Você tem alguma sugestão de modificação no software ou na página WEB?\n";
$contents .= $sugestoesweb;
$contents .= "\n\n";

$contents .= "Você apresentaria este software para seus alunos em sala de aula? Em caso afirmativo, para tratar sobre qual assunto do currículo escolar?\n";
$contents .= $uso;
$contents .= "\n\n";

$contents .= "Dê uma nota de 0 (zero) a 10 (dez) para o software e, se possível, justifique o porquê de sua nota.\n";
$contents .= $nota;
$contents .= "\n\n\n";


$contents .= "Rigor: ";
$contents .= $rigor;
$contents .= "\n";
$contents .= "Interatividade: ";
$contents .= $interatividade;
$contents .= "\n";
$contents .= "Linguagem adequada: ";
$contents .= $linguagem;
$contents .= "\n";
$contents .= "Contextualização: ";
$contents .= $contextualizacao;
$contents .= "\n";
$contents .= "Experimentação: ";
$contents .= $experimentacao;
$contents .= "\n";
$contents .= "Múltiplas abordagens: ";
$contents .= $alternativas;
$contents .= "\n";
$contents .= "Layout: ";
$contents .= $layout;
$contents .= "\n";
$contents .= "Dicas e feedback: ";
$contents .= $dicas;
$contents .= "\n";
$contents .= "Navegação: ";
$contents .= $navegacao;
$contents .= "\n";
$contents .= "Conexão com o mundo real: ";
$contents .= $mundoreal;
$contents .= "\n";
$contents .= "Interdisciplinaridade: ";
$contents .= $interdisciplinaridade;
$contents .= "\n";



$subject = mb_convert_encoding($subject, "UTF-8", "ISO-8859-1");
$contents = mb_convert_encoding($contents, "UTF-8", "ISO-8859-1");

// send mail - $subject & $contents come from surfer input
mail($to1, $subject, $contents, $from_header);
mail($to2, $subject, $contents, $from_header);

$html = "<a href=\"pge-01-br.html\">[Clique aqui para voltar para a página principal!]</a>";

include("top.html");

   print("<br>");
   print("<br>");
   print("<center>");
   print("<strong>");
   print("<h1>");
   print("Obrigado pela sua avaliação!");
   print("</h1>");
   print("<br>");
   print("</strong>");
   print("</center>");
   print("");
   print("<br>");
   print("<br>");
   print("<br>");
   print("<br>");
   print("<p align=\"center\">\n");
   print($html);
   print("</p>\n");

include("bottom.html");


?>
