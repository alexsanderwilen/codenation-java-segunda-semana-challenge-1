package challenge;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {

    final static int MAIORIDADE = 18;
    final static int PONTOSSUSPENSAO = 20;
    final static int LOTACAO = 10;
    final static int IDADEPRIORIDADE = 55;

    private List<Carro> vagas;

    public Estacionamento() {
        this.vagas = new ArrayList<>();
    }

    public void estacionar(Carro carro) {

        if(!existeMotorista(carro.getMotorista())){
            throw new EstacionamentoException("Carro sem motorista");
        }


        if (!motoristaMaiorIdade(carro.getMotorista().getIdade())){
            throw new EstacionamentoException("Motorista Menor Idade");
        }


        if(motoristaCarteiraSuspensa(carro.getMotorista().getPontos())){
            throw new EstacionamentoException("Habilitação suspensa");
        }

        Carro carro1 = null;

        if(estacionamentoLotado()){
            try{
                carro1 = vagas.stream().filter(x -> x.getMotorista().getIdade() < IDADEPRIORIDADE ).findFirst().get();
            }catch(Exception e) {
                carro1 = null;
            }
        }

        if(carro1 != null){
            vagas.remove(carro1);
        }

        if(!estacionamentoLotado()){
            vagas.add(carro);
        }
        else{
            throw new EstacionamentoException("Estacionamento Lotado");
        }
    }

    public int carrosEstacionados() {
        return vagas.size();
    }

    public boolean carroEstacionado(Carro carro) {
        return vagas.stream().filter(x -> x.getMotorista().equals(carro.getMotorista())).findFirst().isPresent();
    }

    public boolean motoristaMaiorIdade(int idade){
        return idade >= MAIORIDADE;
    }


    public boolean existeMotorista(Motorista motorista){
        return motorista != null;
    }

    public boolean motoristaCarteiraSuspensa(int pontos){
        return pontos > PONTOSSUSPENSAO;
    }

    public boolean estacionamentoLotado(){
        return vagas.size() == LOTACAO;
    }

}