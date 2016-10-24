/**
 * @author Eron 
 */

/**isv
 * JAVADOC 
 * 
 * Exemplo de Entrada Inicial dos dados da Gramática:
 * Estados não Terminais (V):	S A B
 * Estado Inicial (S):			S
 * Estados Terminais (T):		x y z
 * Regras de Produção (P):		SA SB AAx AyB BAB BBz Bz
 * 
 * Tradução das Regras de Produção:
 * S -> A
 * S -> B
 * A -> Ax
 * A -> yB
 * B -> Bz
 * B -> z
 * 
 * OBS: As regras de produção tem como padrão um primeiro caracter que
 * 		representa um estado não terminal seguido de uma sequencia de
 * 		caracteres que representam o resultado do que aquele estado produz.
 * 
 * OBS2: Esse código não suporta Epsilon (ε).
 * 
 */

public class Grammar {
	
	private String []RegrasProducao;
	private String iEstado;
	private String NTestado, Testado;
	
	// Construtor
	public Grammar(){}
		
	// Estados não Terminais (V): S A B
	public void setNTestado(String NTestado) throws InvalidGrammarException {

		this.NTestado = NTestado;	
		// É necessário pelo menos 1 Estado Não Terminal (S)
		if (NTestado.length() < 1)
			throw new InvalidGrammarException("Estados Não Terminais Insuficientes!"
				+ "É necessário pelo menos 1 Estado Não Terminal (S)");
		// Verifica se todos os Estados são Não Terminais (letras maiúsculas)
		for (int i = 0; i < NTestado.length(); i+=2) {
			if (!Character.isUpperCase(NTestado.charAt(i)))
				throw new InvalidGrammarException("Estado Terminal em meio aos "
					+ "Estados Não Terminais (" + NTestado.charAt(i) + ")");
		}
		
		System.out.println("Estados não terminais setados com sucesso: " + NTestado);
	}

	// Ex: Estado Inicial (S): S
	public void setiEstado(String iEstado) throws InvalidGrammarException {

		this.iEstado = iEstado;
		
		if (iEstado.length() > 1)
			throw new InvalidGrammarException("Mais de um estado inicial!");
		
		if (!NTestado.contains(iEstado))
			throw new InvalidGrammarException("Estado Inicial não faz parte do grupo de Estados Não Terminais!");
		
		System.out.println("iEstado: " + this.iEstado);
	}

	// Ex: Estados Terminais (T): x y z
	public void setTestado(String Testado) throws InvalidGrammarException {

		this.Testado = Testado;
		
		if (Testado.length() < 1)
			throw new InvalidGrammarException("Estados Terminais Insuficientes!"
				+ "É necessário pelo menos 1 Estado Terminal");
		
		for (int i = 0; i < Testado.length(); i+=2) {
			if (Character.isUpperCase(Testado.charAt(i)))
				throw new InvalidGrammarException("Estado Terminal em meio aos Estados Não Terminais."
					+ "(" + Testado.charAt(i) + ")");
		}
		// OS ESTADOS TERMINAIS SÃO VÁLIDOS!
		System.out.println("Estados terminais setados com sucesso: " + Testado);
	}
	
	// Ex: Regras de Produção (P): SA SB AAx AyB BBz Bz
	public void setRegrasProducao(String stringRegras) throws InvalidGrammarException{
		
		int i, j, length;
		
		this.RegrasProducao = stringRegras.split(" ");
		length = RegrasProducao.length;

		// Testa se todas as Regras De Produção tem tamanho mínimo igual a 2.
		for (i = 0; i < length; i++) {
			if (this.RegrasProducao[i].length() < 2)
				throw new InvalidGrammarException("Regra de Produção inválida! Não leva a lugar nenhum! ("
					+ this.RegrasProducao[i] + ")");
			
			// Testa se todos os caracteres das Regras de Produção fazem parte dos Estados Não Terminais ou Terminais
			for (j = 0; j < this.RegrasProducao[i].length(); j++) {
				if (-1 == this.NTestado.indexOf(this.RegrasProducao[i].charAt(j)) &&
					-1 == this.Testado.indexOf(this.RegrasProducao[i].charAt(j)) )
						throw new InvalidGrammarException(this.RegrasProducao[i].charAt(j) +
							" na Regra de Produção " + this.RegrasProducao[i] +
							" não faz parte dos Estados Não Terminais nem Terminais!");
			}
		}
		
		// Testa se existe algum Estado Não Terminal que não está nas Regras De Produção
		// Não é necessariamente um erro, porém será tratado como um.
		for (i = 0; i < this.NTestado.length(); i+=2) {
			if ( -1 == stringRegras.indexOf(this.NTestado.charAt(i)))
				throw new InvalidGrammarException("Estado Não Terminal " + this.NTestado.charAt(i) +
					" não está sendo utilizado em nenhuma regra de produção!");
		}
		
		// Testa se existe algum Estado Terminal que não está nas Regras De Produção
		// Não é necessariamente um erro, porém será tratado como um.
		for (i = 0; i < this.Testado.length(); i+=2) {
			if ( -1 == stringRegras.indexOf(this.NTestado.charAt(i)))
				throw new InvalidGrammarException("Estado Terminal não está sendo utilizado em nenhuma regra de produção! (" +
					this.NTestado.charAt(i) + ")");
		}
	}
	
	// Procura e retorna um Estado Não Terminal (o primeiro)
	private String searchNonTerminalState(String palavra) {
		int i;
		for (i = 0; i < palavra.length(); i++) {
			if (Character.isUpperCase(palavra.charAt(i)))
				return String.valueOf(palavra.charAt(i));
		}
		return null;
	}
	
	// Testa de o input é válido.
	public boolean isValid(String palavra, String input) {
		
		String estadoNaoTerminal, inicio, meio, fim;
		StringBuilder stringTemp;
		
		// se a palavra tiver o mesmo tamanho do input e for igual, a palavra foi reconhecida
		if (palavra.equals(input))
			return true;
		
		// se a palavra ficar maior que o input, não deverá continuar a recursão
		if (palavra.length() > input.length())
			return false;
		
		// Pega o primeiro Estado Não Terminal da string
		// Se retornar null, não há como crescer a palavra.

		estadoNaoTerminal = this.searchNonTerminalState(palavra);
		
		if (estadoNaoTerminal == null)
			return false;
		
		// Separa a string em 3 partes para reconstruir uma nova string
		inicio = palavra.substring(0, palavra.indexOf(estadoNaoTerminal));
		fim = "";
		fim = palavra.substring(palavra.indexOf(estadoNaoTerminal), palavra.length()-1);
		
		// Percorre as Regras De Produção e pega as regras válidas para o estadoNaoTerminal
		// Para cada regra válida, é feita uma nova string para continuar a recursão e testar se é válida.
		for (int i = 0; i < this.RegrasProducao.length; i++) {
			
			if (estadoNaoTerminal.charAt(0) == this.RegrasProducao[i].charAt(0)) {
				
				// Constrói a nova String
				stringTemp = new StringBuilder();
				stringTemp.append(inicio);
				meio = this.RegrasProducao[i].substring(1, this.RegrasProducao[i].length());
				stringTemp.append(meio);
				stringTemp.append(fim);
				palavra = stringTemp.toString();
				
				// continua a recursão
				if (isValid(palavra, input))
					return true;
			}
		}
		return false;
	}
	
	// Método para o usuário entrar com os dados.
	public boolean input(String input) {
		return isValid(String.valueOf(this.NTestado.charAt(0)), input);
	}
}
