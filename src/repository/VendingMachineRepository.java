package repository;

import model.Beverage;
import model.UserWallet;
import model.VendingMachineWallet;
import util.FileManager;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineRepository {
    VendingMachineWallet vendingMachineWallet;   // 자판기 보유 금액
    UserWallet userWallet; // 사용자 입력금액
    ArrayList<Beverage> beverages;   // 음료 데이터

    public VendingMachineRepository() {
        // 돈통
        vendingMachineWallet = new VendingMachineWallet();
        userWallet = new UserWallet();

        // 파일에서 데이터 불러와서 저장
        loadVendingMachine(beverages, vendingMachineWallet);
    }

    public UserWallet getUserWallet() {
        return userWallet;
    }
    public VendingMachineWallet getVendingMachineWallet() {
        return vendingMachineWallet;
    }
    public ArrayList<Beverage> getBeverages() {
        return beverages;
    }

    private void loadVendingMachine(ArrayList<Beverage> beverages, VendingMachineWallet vendingMachineWallet) {

        String[] dataLines = FileManager.loadFromFile("src/resources/beverages.txt");
        for (String line : dataLines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 3) {
                try {
                    String name = parts[0];
                    int price = Integer.parseInt(parts[1]);
                    int stock = Integer.parseInt(parts[2]);
                    beverages.add(new Beverage(name, price, stock));
                } catch (NumberFormatException e) {
                    System.err.println("숫자 파싱 오류: " + line);
                }
            } else {
                System.err.println("형식 오류: " + line);
            }
        }
        dataLines = FileManager.loadFromFile("src/resources/wallet.txt");
        for(String line : dataLines) {
            String[] tokens = line.trim().split("\\s+");
            if (tokens.length % 2 != 0) {
                System.err.println("잘못된 형식: 짝이 맞지 않는 화폐 단위 및 개수");
                return;
            }

            for (int i = 0; i < tokens.length; i += 2) {
                try {
                    int denomination = Integer.parseInt(tokens[i]);
                    int count = Integer.parseInt(tokens[i + 1]);
                    vendingMachineWallet.insertNode(denomination, count);
                } catch (NumberFormatException e) {
                    System.err.println("숫자 파싱 오류: " + tokens[i] + " 또는 " + tokens[i + 1]);
                }
            }
        }
    }

    public void saveVendingMachine(){
        List<String> dataList = new ArrayList<>();

        for (Beverage b : this.beverages) {
            String line = b.getName() + " " + b.getPrice() + " " + b.getStock();
            dataList.add(line);
        }

        // 기존 저장 메서드에 전달
        FileManager.saveToFile("src/resources/beverages.txt", dataList); // ← 저장 로직

        StringBuilder sb = new StringBuilder();
        int[] denominations = {10, 50, 100, 500, 1000};

        for (int denom : denominations) {
            Integer count = vendingMachineWallet.get(denom);
            if (count != null && count > 0) {
                sb.append(denom).append(" ").append(count).append(" ");
            }
        }

        dataList = new ArrayList<>();
        dataList.add(sb.toString().trim());  // 한 줄만 넣기 (불필요한 공백 제거)

        FileManager.saveToFile("src/resources/wallet.txt", dataList); // ← 저장 로직
    }
}


