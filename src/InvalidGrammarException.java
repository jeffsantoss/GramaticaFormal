
@SuppressWarnings("serial")
public class InvalidGrammarException extends Exception {
	
	private String erro;
	
	public InvalidGrammarException(String erro) {
		this.erro = erro;
	}
	
	public String getErro(){
		return this.erro;
	}
}
