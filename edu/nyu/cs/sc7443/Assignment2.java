package edu.nyu.cs.sc7443;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Assignment2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Random rng = new Random();
		boolean gameWon = false;
		String whoWon = "n";
		boolean playerStanding = false;
		boolean dealerStanding = false;

		ArrayList playerCards = new ArrayList();
		playerCards.add(rng.nextInt(9) + 2);
		playerCards.add(rng.nextInt(9) + 2);

		ArrayList dealerCards = new ArrayList();
		dealerCards.add(rng.nextInt(9) + 2);
		dealerCards.add(rng.nextInt(9) + 2);

		int pCardSum = 0;
		int dCardSum = 0;

		for (Object pCard : playerCards) {
			pCardSum += (int) pCard;
		}
		for (Object dCard : dealerCards) {
			dCardSum += (int) dCard;
		}

		while (gameWon != true) {

			System.out.println("Your cards are " + playerCards);

			if (pCardSum > 21) {
				System.out.println("You went bust!");
				whoWon = "d";
				break;
			} else if (dCardSum > 21) {
				System.out.println("The dealer went bust!");
				whoWon = "p";
				break;
			} else if (playerStanding == false) {
			System.out.println("Would you like to hit or stand?");

			String action = scanner.nextLine();

			if (action.equals("hit")) {
				 playerCards.add(rng.nextInt(9) + 2);

				 pCardSum += (int) playerCards.get(playerCards.size() - 1);

				 if (pCardSum > 21) {
				 	System.out.println(playerCards);
				 	System.out.println("You went bust!");
				 	gameWon = true;
				 	whoWon = "d";
				 	break;
				 } else if (pCardSum == 21) {
				 	System.out.println(playerCards);
				 	System.out.println("You got blackjack!");
				 	gameWon = true;
				 	whoWon = "p";
				 	break;
				 }
				} else {
					playerStanding = true;
				}
			}

			if (dCardSum < 15) {
				dealerCards.add(rng.nextInt(9) + 2);

				dCardSum += (int) dealerCards.get(dealerCards.size() - 1);

				if (dCardSum > 21) {
					System.out.println("The dealer went bust!");
					gameWon = true;
					whoWon = "p";
				} else if (dCardSum == 21) {
				 	System.out.println("The dealer got blackjack!");
				 	gameWon = true;
				 	whoWon = "d";
				 	break;
				}
			} else {
				dealerStanding = true;
			}

			if (playerStanding && dealerStanding) {
				if (pCardSum > dCardSum) {
					gameWon = true;
					whoWon = "p";
				} else if (dCardSum > pCardSum) {
					gameWon = true;
					whoWon = "d";
				} else {
					gameWon = true;
					whoWon = "t";
				}
			}
		}

		System.out.println("The dealer\'s cards were " + dealerCards);

		switch (whoWon) {
			case "p":
				System.out.println("Congratulations, you won!");
				break;
			case "d":
				System.out.println("Sorry, the dealer won.");
				break;
			case "n":
				System.out.println("Something\'s wrong...");
				break;
			case "t":
				System.out.println("It\'s a tie!");
				break;
			default:
				System.out.println("Something is really wrong!");
		}
	}
}