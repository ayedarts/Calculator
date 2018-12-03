
@SuppressWarnings("serial")
class ParsingException extends Exception {
	public ParsingException(Operator o){
		System.out.println("opérande manquant pour l’opérateur " + o.toString());
	}
	public ParsingException(boolean ouvr){
		if (ouvr) {
			System.out.println("parenthèse ouvrante manquante" );
		} else {
			System.out.println("parenthèse fermante manquante" );
		}
	}
	public ParsingException(double d) {
		System.out.println("point inattendu après le nombre " + d);
	}
	public ParsingException(char c) {
		System.out.println("caractère ‘"+c+"’ inconnu");
	}
}
