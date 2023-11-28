import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Card {

    static final int QTDE_NUM_SORTEADOS_POR_VEZ = 5;
    static final int QTDE_NUM_CARTELA = 5;
    static final int QTDE_NUMEROS = 60;
    static Scanner scanner = new Scanner(System.in);
    static int[] listDrawNumbers = new int[QTDE_NUMEROS];
    static boolean eBingo = false;
    static int round = 0;
    static Random rnd = new Random();
    public static void main(String[] args) {

        System.out.println("********************************************************************");
        System.out.println("             ** ** Bem-vindo ao Bingo Ser+ Tech 50+ ** **           ");
        System.out.println("********************************************************************");

        System.out.println("Digite os nomes dos jogadores separados por hífen (ex: player1-player2-player3):");
        String input = scanner.next();
        String[] playerNames = input.split("-");

        String beginOption;

        System.out.println("Escolha a opção desejada: ");
        System.out.println(" 'A' para automático, 'M' para manual : ");
        beginOption = scanner.next();

        int[][]cardNumbers = new int[playerNames.length][QTDE_NUM_CARTELA];

        if (beginOption.equals("A")) {
            System.out.println("Opção escolhida: Automática");
            playAutomatic(cardNumbers,playerNames);
        } else if (beginOption.equals("M")) {
            System.out.println("Opção escolhida: Manual");
            playManual(cardNumbers,playerNames);

        }

        System.out.println("Lista de Participantes: " );
        for (int i = 0; i <playerNames.length ; i++) {
            System.out.printf("%2d -  %15s -  " , i+1, playerNames [i] );
            System.out.println(Arrays.toString(cardNumbers[i]));
        }
        System.out.println("Escolha a opção de sorteio desejada: ");
        System.out.println(" 'A' para automático, 'M' para manual : ");
        String drawOption = scanner.next();
        if (drawOption.equals("A")) {
            System.out.println("Opção escolhida: Automática");
            automaticDraw(cardNumbers, playerNames);

        } else if (drawOption.equals("M")) {
            System.out.println("Opção escolhida: Manual");
            manualDraw(cardNumbers, playerNames);

        }
    }
    public static int[][] playAutomatic(int[][] cardNumbers, String[] playerNames){
        for (int i = 0; i < playerNames.length ; i++) {
            int[] cards = new int[QTDE_NUM_CARTELA];

                for (int k= 0; k < cards.length; k++){
                int number = rnd.nextInt(QTDE_NUMEROS) + 1;
                for (int j = 0; j < QTDE_NUM_CARTELA ; j++) {
                    if (cards[j] == number){
                        number = rnd.nextInt(QTDE_NUMEROS) + 1;
                        j = -1;
                    }
                }
                cards[k] = number;
            }
            cardNumbers[i] = cards ;
        }
    return cardNumbers;
    }

     public static int[][] playManual(int[][] cardNumbers, String[] playerNames) {
        System.out.println("Digite todas as cartelas da sorte separando os números por vírgula e as cartelas por hífen (ex: 1,2,3,4,5-6,7,8,9,1-2,3,4,5,6)");
        String input = scanner.next();

        String[] cards = input.split("-");

        int[] numbers = new int[QTDE_NUM_CARTELA];

        for (int j = 0; j < cards.length; j++) {
            String[] stringV = cards[j].split(",");

            for (int i = 0; i < stringV.length ; i++) {
                numbers [i] = Integer.parseInt(stringV[i]);
                cardNumbers[j][i] = numbers[i];
            }
        }
    return cardNumbers;
    }
    public static void manualDraw(int[][]cardNumbers, String[]playerNames){
        System.out.println("Iniciando sorteio manual...");
        boolean stay = true;
        while(stay){
            round +=1;
            System.out.println("Digite os 5 números sorteados nessa rodada separados por vírgula " +
                    "(ex: 1,2,3,4,5)");

            String drawNumbers = scanner.next();
            String[] stringV = drawNumbers.split(",");
            int[] numbers = new int[QTDE_NUM_SORTEADOS_POR_VEZ];

            for (int i = 0; i < stringV.length ; i++) {
                int number = Integer.parseInt(stringV[i]);
                numbers [i] = number;
                for (int j = 0; j < listDrawNumbers.length; j++) {
                    if (listDrawNumbers[j] == 0){
                        listDrawNumbers [j] = number;
                        break;
                    }
                }
            }

            System.out.printf("rodada %d\n", round );
            System.out.println("Os números sorteados foram: " +
                    Arrays.toString(numbers));

            checkCards(listDrawNumbers, cardNumbers, playerNames);
            if (eBingo) {
                break;
            }

            System.out.println("Para continuar arriscando digite Y ou se quiser sair digite X");
            String riskOption = scanner.next();

            if (riskOption.equals("X")) {
                System.out.println("Opção escolhida: Sair. Partida encerrada!");
                stay = false;
            }
        }
        endGame(cardNumbers,playerNames);
        System.out.println();
        checkCards(listDrawNumbers, cardNumbers, playerNames);
    }

    public static void automaticDraw(int[][]cardNumbers, String []playerNames){
        System.out.println("Iniciando sorteio automático...");

        boolean stay = true;
        while (stay){
            round +=1;
            int[] automaticDrawNumbers = new int[QTDE_NUM_SORTEADOS_POR_VEZ];

            for (int k= 0; k < automaticDrawNumbers.length; k++){
                int number = rnd.nextInt(QTDE_NUMEROS) + 1;
                for (int j = 0; j < QTDE_NUMEROS ; j++) {
                    if (listDrawNumbers[j] == number){
                        number = rnd.nextInt(QTDE_NUMEROS) + 1;
                        j = -1;
                    }
                }
                automaticDrawNumbers[k] = number;
                for (int i = 0; i < QTDE_NUMEROS; i++) {
                    if (listDrawNumbers[i] == 0){
                        listDrawNumbers [i] = number;
                        break;
                    }
                }
            }
            System.out.printf("rodada %d\n", round );
            System.out.println("Os números sorteados foram: " +
                    Arrays.toString(automaticDrawNumbers));

            checkCards(listDrawNumbers, cardNumbers, playerNames);
            if (eBingo) {
                break;
            }

            System.out.println("Para continuar arriscando digite Y ou se quiser sair digite X");
            String riskOption = scanner.next();

            if (riskOption.equals("X")) {
                System.out.println("Opção escolhida: Sair. Partida encerrada!");
                stay = false;
            }
        }
        endGame(cardNumbers,playerNames);
        System.out.println();
        checkCards(listDrawNumbers, cardNumbers, playerNames);
    }
    public static void checkCards(int []listDrawNumbers, int [][]cardNumbers,
                                  String []playerNames){
        int[][]checkList = new int[cardNumbers.length][QTDE_NUM_CARTELA];
        for (int i = 0; i < cardNumbers.length; i++) {
            for (int j = 0; j < QTDE_NUM_CARTELA; j++) {
                for (int k = 0; k < listDrawNumbers.length; k++) {
                    if (cardNumbers[i][j] == listDrawNumbers[k]){
                        checkList[i][j] = 1;
                    }
                }
            }
        }

        int [] score = new int[cardNumbers.length];
        for (int i = 0; i < checkList.length; i++) {
           int add = 0;
            for (int j = 0; j < QTDE_NUM_CARTELA; j++) {
                add += checkList[i][j];
            }
            score[i]= add;
        }
      eBingo(score);
        if (eBingo) {
            ranking(score,playerNames,playerNames.length);

        }else {
            ranking(score,playerNames,3);
        }

    }
    public static void ranking(int[] score, String[] playerNames, int place){
        int[][] auxiliar = new int[playerNames.length][2];

        for (int i = 0; i < score.length; i++) {
            auxiliar[i][0] = score[i];
            auxiliar[i][1] = i;
        }

        Arrays.sort(auxiliar, (a,b)-> b[0] - a[0]);

        int[] rank = new int[score.length];
        for (int i = 0; i < playerNames.length; i++) {
            rank[auxiliar[i][1]] = i;
        }

        for (int i = 0; i < place ; i++) {
            for (int j = 0; j < score.length ; j++) {
                if (i == rank[j]){
                    System.out.printf("%d - %s  com %d pontos \n", i+1,playerNames[j],
                            score[j]);
                }
            }
        }
    }
    public static void eBingo(int [] score){
        for (int i = 0; i < score.length; i++) {
            if (score[i] == 5){
               eBingo = true;

            }
        }
    }
    public static void endGame(int[][]cardNumbers, String[] playerNames){
        System.out.println("******  É BINGO  ******");
        System.out.printf("O total de rodadas foi %d  \n", round);
        System.out.println("Os números sorteados foram:  " );

        for (int i = 0; i < listDrawNumbers.length; i++) {
            if (listDrawNumbers [i]== 0 ) {
                break;
            }
            System.out.printf("%d ",listDrawNumbers[i]);
        }
    }
}









