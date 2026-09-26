import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        String opcao;

        do {
            opcao = JOptionPane.showInputDialog(null,"Bem vindo ao sistema do hotel\n" + "Digite uma opção:\n" + "[1] Cadastrar hóspede\n" + "[2] Cadastrar quarto\n" + "[3] Fazer uma reserva\n" + "[4] listar hóspedes\n" + "[5] listar quartos\n" + "[0] Sair");
            if (opcao == null) {
                break;
            }
            switch (opcao) {
                case "1":
                    String nome;
                    do {
                        nome = JOptionPane.showInputDialog(null, "Digite o nome do hóspede:");
                        if (nome == null) {
                            break;
                        }
                        if (nome.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O nome precisa ser preenchido!");
                        }
                    }while (nome.trim().isEmpty());
                    if (nome == null) {
                        break;
                    }
                    String cpf;
                    boolean cadastrado = false;
                    do {
                        cpf = JOptionPane.showInputDialog(null, "Digite o CPF do hóspede:");
                        if (cpf == null) {
                            break;
                        }
                        if (cpf.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O CPF precisa ser preenchido!");
                            continue;
                        }
                        Hospede hospede = new Hospede(nome, cpf);
                        cadastrado = hotel.cadastrarHospede(hospede);
                        if (!cadastrado) {
                            JOptionPane.showMessageDialog(null, "CPF já cadastrado, digite outro!");
                        }
                    }while (!cadastrado);
                    if (cpf == null) {
                        break;
                    }
                    JOptionPane.showMessageDialog(null, "Hóspede cadastrado com sucesso!");
                    break;
                case "2":
                    String tipo;
                    do {
                        tipo = JOptionPane.showInputDialog(null, "Digite o tipo do quarto:");
                        if (tipo == null) {
                            break;
                        }
                        if (tipo.trim().isEmpty()){
                            JOptionPane.showMessageDialog(null, "O tipo de quarto precisa ser preenchido!");
                        }
                    }while (tipo.trim().isEmpty());
                    if (tipo == null){
                        break;
                    }

                    String numeroTexto;
                    Integer numero = null;
                    do {
                        numeroTexto = JOptionPane.showInputDialog(null, "Digite o número do quarto:");
                        if (numeroTexto == null) {
                            break;
                        }
                        if (numeroTexto.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O número precisa ser preenchido!");
                            continue;
                        }
                        try {
                            numero = Integer.valueOf(numeroTexto);
                        } catch (NumberFormatException e) {
                            numero = null;
                            JOptionPane.showMessageDialog(null, "Digite apenas números!");
                        }
                    }while (numero == null);
                    if (numero == null) {
                        break;
                    }

                    String capacidadeTexto;
                    Integer capacidade = null;
                    do {
                        capacidadeTexto = JOptionPane.showInputDialog(null, "Digite a capacidade do quarto");
                        if (capacidadeTexto == null) {
                            break;
                        }
                        if (capacidadeTexto.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "A capacidade deve ser preenchida!");
                            continue;
                        }
                        try {
                            capacidade = Integer.valueOf(capacidadeTexto);
                        }catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Digite apenas números!");
                        }

                    }while (capacidade == null);
                    if (capacidade == null) {
                        break;
                    }
                    break;
                case "3":
                    JOptionPane.showMessageDialog(null, "Fazer reserva");
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "0":
                    JOptionPane.showMessageDialog(null, "Saindo...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }while (!opcao.equals("0"));
    }
}
