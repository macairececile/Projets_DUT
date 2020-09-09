#!/bin/bash

# Projet réalisé par MACAIRE Cécile S1D

# Permet de ne pas prendre en compte les majuscules et minuscules
shopt -s nocasematch

######################################## FONCTIONS ###########################################

# La fonction menu affiche le menu
function menu {
	echo "1. Voir"
	echo "2. Editer"
	echo "3. Générer"
	echo "4. Lancer"
	echo "5. Débugger"
	echo "6. Imprimer"
	echo "7. Shell"
	echo "8. Quitter"
	
}

# La fonction pager permet de lire le fichier page par page	
# Nous utilisons la variable d'environnement PAGER
function voir {
	echo "Vous venez de choisir la commande 1" 
	pager $fichier  
}

# La fonction editor permet d'écrire dans le fichier
# Nous utilisons la variable d'environnement EDITOR
function editer {
	echo "Vous venez de choisir la commande 2, vous allez pouvoir éditer $fichier"
	editor $fichier
}

# La fonction generer permet de compiler le fichier 
function generer {
	clear
	echo "Vous venez de choisir la commande 3" 
	echo "Voulez-vous compiler le fichier ? (oui | non)"
	read ouinon
	case $ouinon in 
	oui) 
		g++ $fichier 2> /tmp/erreurs.txt
		erreur=$( cat /tmp/erreurs.txt | grep ": error: " /tmp/erreurs.txt | wc -l)
		echo 
			# On teste le nombre d'erreur du fichier
			if test $erreur = 0
			then
				mv 'a.out' /tmp
				echo "Le fichier a été compilé avec succès"
				echo "Vous allez être redirigé vers le menu"
			else
				echo "Il y a "$erreur" erreurs dans "$fichier", voulez-vous les consulter ? (Y | N)"
				read yesno 
				case $yesno in
				Y)
					echo "Vous allez consulter les erreurs"
					pager /tmp/erreurs.txt
					;;
				N)
					echo "Vous allez être redirigé vers le menu"
					menu
					;;
				esac
			fi	
		;;
	non)
		echo "Vous allez être redirigé vers le menu"
		;;
	*)
		echo "Vous allez être redirigé vers le menu"
		;;
	esac
}


# Vérifie si le fichier existe et est spécifié comme étant exécutable avec -x
# Vérifie si l'utilisateur a les droits d'exécution  
# Lance le programme exécutable 
function lancer {
	if test -x /tmp/a.out
	then 
		echo "Votre fichier exécutable existe"
		echo "Voulez-vous lancer votre programme ? (oui | non)"
		read choixlancer
		case $choixlancer in 
		oui)		 
			/tmp/a.out
			;;
		non)
			echo "Vous allez être redirigé vers le menu "
			;;
		*)
			echo "Vous allez être redirigé vers le menu "
			;;
		esac
		
	else
		chmod 700 /tmp/a.out
		/tmp/a.out
	fi
}		 

# Compile le programme en mode debug 
# On utilise g++ -g	
function debugguer {
	clear
	echo "Vous venez de choisir la commande 5"
	g++ -g $fichier -o /tmp/a.out
	if test -e /tmp/a.out
	then 
		echo "Voulez-vous compiler le fichier en mode debug ? (oui | non)"
		read ouinon
		case $ouinon in 
		oui) 
			gdb a.out
			clear
			;;
		non)
			echo "Vous allez être redirigé vers le menu"
			;;
		*)
			echo "Vous allez être redirigé vers le menu"
			;;
		esac
	fi	
}
# Imprime le fichier C++. Au préalable, l'utilisateur a un apercu en pdf
function imprimer {	
	echo "Quel non voulez vous donner à votre fichier pdf ?"
	read nompdf
	a2ps -q $1 -o pdf.ps
	ps2pdf -q pdf.ps $nompdf
	rm pdf.ps
	xpdf $nompdf.pdf &
}

# Lance un autre shell avec un prompt différent
# Affiche un message qui averti que l'utilisateur devra taper exit 
# pour revenir dans le script
function shell {
	echo "Vous allez être redirigé vers un autre shell"
	xterm -e "echo Vous devrez taper exit pour revenir dans ce script ; $SHELL"
}

# Fonction qui arrête définitivement le script 
# Un message est affiché pour savoir si l'utilisateur 
# est sur de vouloir quitter le script	
function quitter {
	echo "Etes-vous sur de vouloir quitter le shell ? (oui | non)"
	read choix 
	case $choix in
	oui)
		echo "Vous avez décidé de quitter le shell"
		echo "Au revoir"
		exit 1 
		;;
	non)
		echo "Bon choix ! Vous allez être redirigé vers le menu"
		;;
	*)
		echo "Vous allez être redirigé vers le menu"
		;;
	esac				
}

################ MAIN DU SHELL ##################

# Si l'utilisateur utilise l'option -h alors un message s'affiche
 
if test "$1" = "-h"
then 
	echo "Usage: ./gestionnaire.sh [fichier]"
	echo "fichier est un fichier C++ existant ou pas, donné sans extension."
	exit 0
else

# Cette partie du scipt permet de vérifier si un fichier avec l'extension .cpp existe
# Si le fichier n'existe pas, alors le fichier va être créé avec un template spécifique 
if test -e $1.cpp 
then
	echo "Le fichier existe."
	fichier="$1.cpp"
else
		echo "Erreur: pas de fichier $1.cpp"
		echo "Le fichier va être créé"
		echo "Entrer un nom de fichier : "
		read nomfichier
		touch "$nomfichier.cpp"
		fichier=$nomfichier.cpp			
		echo "#include <iostream> ">> $fichier
		echo "using namespace std; " >> $fichier
		echo "int main(int argc, char **argv){" >> $fichier
		echo "	cout << ""Hello World ! "" << endl;" >> $fichier
		echo "        return 0;" >> $fichier
		echo "}" >> $fichier
fi
fi


# Boucle while pour l'affichage du menu et le choix de la commande
while [ "$numero"=="8" ]
do 
	menu
	echo "Veuillez choisir le numéro de la commande :"
	read numero 
	case $numero in 
	1)
		voir
		;;
	2)
		editer
		;;
	3)
		generer
		;;
	4)
		lancer
		;;
	5) 
		debugguer
		;;
	6) 
		imprimer
		;;
	7)
		shell
		;;
	8)
		quitter
		;;
	*)
		echo "Veuillez retaper le numéro de la commande"
	esac
done	 
