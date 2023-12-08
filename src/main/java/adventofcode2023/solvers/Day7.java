package adventofcode2023.solvers;

import adventofcode2023.fileloaders.FileLoaders;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 {

    public static String Puzzle1(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        List<Hand> hands = lines.stream().map(line -> {
            List<String> lineParts = Arrays.stream(line.split(" ")).toList();
            return new Hand(lineParts.get(0), Integer.parseInt(lineParts.get(1)));
        }).toList();

        List<ParsedHand> parsedHands = hands.stream().map(Day7::calculateRank).toList();

        List<ParsedHand> fiveOfKinds = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.FIVE_OF_KIND)).sorted(Day7::compare).toList();
        List<ParsedHand> fourOfKinds = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.FOUR_OF_KIND)).sorted(Day7::compare).toList();
        List<ParsedHand> fullHouses = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.FULL_HOUSE)).sorted(Day7::compare).toList();
        List<ParsedHand> threeOfKinds = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.THREE_OF_KIND)).sorted(Day7::compare).toList();
        List<ParsedHand> twoPairs = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.TWO_PAIR)).sorted(Day7::compare).toList();
        List<ParsedHand> onePairs = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.ONE_PAIR)).sorted(Day7::compare).toList();
        List<ParsedHand> highCards = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.HIGH_CARD)).sorted(Day7::compare).toList();

        List<ParsedHand> sortedHands = new ArrayList<>();
        sortedHands.addAll(fiveOfKinds);
        sortedHands.addAll(fourOfKinds);
        sortedHands.addAll(fullHouses);
        sortedHands.addAll(threeOfKinds);
        sortedHands.addAll(twoPairs);
        sortedHands.addAll(onePairs);
        sortedHands.addAll(highCards);

        Collections.reverse(sortedHands);

        Integer sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum += sortedHands.get(i).bid() * (i + 1);
        }

        return String.valueOf(sum);
    }

    private static ParsedHand calculateRank(Hand hand) {

        List<String> cards = Arrays.stream(hand.hand().split("")).toList();
        Map<String, Long> countOfCards = cards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Card> enumeratedCards = cards.stream().map(Card::get).toList();

        if (cards.stream().distinct().count() == 1) {
            return new ParsedHand(Rank.FIVE_OF_KIND,
                    enumeratedCards,
                    hand.bid());
        }

        if (cards.stream().distinct().count() == 2) {
            if (countOfCards.containsValue(4L)) {
                return new ParsedHand(Rank.FOUR_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            return new ParsedHand(Rank.FULL_HOUSE,
                    enumeratedCards,
                    hand.bid());
        }

        if (cards.stream().distinct().count() == 3) {
            if (countOfCards.containsValue(3L)) {
                return new ParsedHand(Rank.THREE_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            return new ParsedHand(Rank.TWO_PAIR,
                    enumeratedCards,
                    hand.bid());
        }

        if (cards.stream().distinct().count() == 4) {
            return new ParsedHand(Rank.ONE_PAIR,
                    enumeratedCards,
                    hand.bid());
        }

        return new ParsedHand(Rank.HIGH_CARD,
                enumeratedCards,
                hand.bid());
    }

    private static ParsedHandP2 calculateRankP2(Hand hand) {

        List<String> cards = Arrays.stream(hand.hand().split("")).toList();
        Map<String, Long> countOfCards = cards.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<CardP2> enumeratedCards = cards.stream().map(CardP2::get).toList();

        if (cards.stream().distinct().count() == 1) {
            return new ParsedHandP2(Rank.FIVE_OF_KIND,
                    enumeratedCards,
                    hand.bid());
        }

        if (cards.stream().distinct().count() == 2) {
            if (enumeratedCards.contains(CardP2.JOKER)) {
                return new ParsedHandP2(Rank.FIVE_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            if (countOfCards.containsValue(4L)) {
                return new ParsedHandP2(Rank.FOUR_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            return new ParsedHandP2(Rank.FULL_HOUSE,
                    enumeratedCards,
                    hand.bid());
        }

        if (cards.stream().distinct().count() == 3) {
            if (countOfCards.containsValue(3L) && (
                    enumeratedCards.stream().filter(c -> c.equals(CardP2.JOKER)).count() == 1 ||
                            enumeratedCards.stream().filter(c -> c.equals(CardP2.JOKER)).count() == 3)) {
                return new ParsedHandP2(Rank.FOUR_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            if (countOfCards.containsValue(3L)) {
                return new ParsedHandP2(Rank.THREE_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            if (enumeratedCards.stream().filter(c -> c.equals(CardP2.JOKER)).count() == 2) {
                return new ParsedHandP2(Rank.FOUR_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            if (enumeratedCards.stream().filter(c -> c.equals(CardP2.JOKER)).count() == 1) {
                return new ParsedHandP2(Rank.FULL_HOUSE,
                        enumeratedCards,
                        hand.bid());
            }

            return new ParsedHandP2(Rank.TWO_PAIR,
                    enumeratedCards,
                    hand.bid());
        }

        if (cards.stream().distinct().count() == 4) {
            if (enumeratedCards.stream().filter(c -> c.equals(CardP2.JOKER)).count() == 1 ||
                    enumeratedCards.stream().filter(c -> c.equals(CardP2.JOKER)).count() == 2) {
                return new ParsedHandP2(Rank.THREE_OF_KIND,
                        enumeratedCards,
                        hand.bid());
            }

            return new ParsedHandP2(Rank.ONE_PAIR,
                    enumeratedCards,
                    hand.bid());
        }

        if (enumeratedCards.stream().filter(c -> c.equals(CardP2.JOKER)).count() == 1) {
            return new ParsedHandP2(Rank.ONE_PAIR,
                    enumeratedCards,
                    hand.bid());
        }

        return new ParsedHandP2(Rank.HIGH_CARD,
                enumeratedCards,
                hand.bid());
    }

    private static int compare(ParsedHand o1, ParsedHand o2) {
        switch (Integer.compare(o1.orderedCards.get(0).getOrder(), o2.orderedCards.get(0).getOrder())) {
            case -1:
                return -1;
            case 1:
                return 1;
            case 0: {
                switch (Integer.compare(o1.orderedCards.get(1).getOrder(), o2.orderedCards.get(1).getOrder())) {
                    case -1:
                        return -1;
                    case 1:
                        return 1;
                    case 0: {
                        switch (Integer.compare(o1.orderedCards.get(2).getOrder(), o2.orderedCards.get(2).getOrder())) {
                            case -1:
                                return -1;
                            case 1:
                                return 1;
                            case 0: {
                                switch (Integer.compare(o1.orderedCards.get(3).getOrder(), o2.orderedCards.get(3).getOrder())) {
                                    case -1:
                                        return -1;
                                    case 1:
                                        return 1;
                                    case 0: {
                                        switch (Integer.compare(o1.orderedCards.get(4).getOrder(), o2.orderedCards.get(4).getOrder())) {
                                            case -1:
                                                return -1;
                                            case 1:
                                                return 1;
                                            case 0:
                                                return 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            default:
                return 0;
        }
    }

    public static String Puzzle2(String input) {
        List<String> lines = FileLoaders.loadInputIntoStringList(input);

        List<Hand> hands = lines.stream().map(line -> {
            List<String> lineParts = Arrays.stream(line.split(" ")).toList();
            return new Hand(lineParts.get(0), Integer.parseInt(lineParts.get(1)));
        }).toList();

        List<ParsedHandP2> parsedHands = hands.stream().map(Day7::calculateRankP2).toList();

        List<ParsedHandP2> fiveOfKinds = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.FIVE_OF_KIND)).sorted(Day7::compareP2).toList();
        List<ParsedHandP2> fourOfKinds = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.FOUR_OF_KIND)).sorted(Day7::compareP2).toList();
        List<ParsedHandP2> fullHouses = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.FULL_HOUSE)).sorted(Day7::compareP2).toList();
        List<ParsedHandP2> threeOfKinds = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.THREE_OF_KIND)).sorted(Day7::compareP2).toList();
        List<ParsedHandP2> twoPairs = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.TWO_PAIR)).sorted(Day7::compareP2).toList();
        List<ParsedHandP2> onePairs = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.ONE_PAIR)).sorted(Day7::compareP2).toList();
        List<ParsedHandP2> highCards = parsedHands.stream().filter(hand -> hand.rank.equals(Rank.HIGH_CARD)).sorted(Day7::compareP2).toList();

        List<ParsedHandP2> sortedHands = new ArrayList<>();
        sortedHands.addAll(fiveOfKinds);
        sortedHands.addAll(fourOfKinds);
        sortedHands.addAll(fullHouses);
        sortedHands.addAll(threeOfKinds);
        sortedHands.addAll(twoPairs);
        sortedHands.addAll(onePairs);
        sortedHands.addAll(highCards);

        Collections.reverse(sortedHands);

        Integer sum = 0;
        for (int i = 0; i < 1000; i++) {
            sum += sortedHands.get(i).bid() * (i + 1);
        }

        return String.valueOf(sum);
    }

    private static int compareP2(ParsedHandP2 o1, ParsedHandP2 o2) {
        switch (Integer.compare(o1.orderedCards.get(0).getOrder(), o2.orderedCards.get(0).getOrder())) {
            case -1:
                return -1;
            case 1:
                return 1;
            case 0: {
                switch (Integer.compare(o1.orderedCards.get(1).getOrder(), o2.orderedCards.get(1).getOrder())) {
                    case -1:
                        return -1;
                    case 1:
                        return 1;
                    case 0: {
                        switch (Integer.compare(o1.orderedCards.get(2).getOrder(), o2.orderedCards.get(2).getOrder())) {
                            case -1:
                                return -1;
                            case 1:
                                return 1;
                            case 0: {
                                switch (Integer.compare(o1.orderedCards.get(3).getOrder(), o2.orderedCards.get(3).getOrder())) {
                                    case -1:
                                        return -1;
                                    case 1:
                                        return 1;
                                    case 0: {
                                        switch (Integer.compare(o1.orderedCards.get(4).getOrder(), o2.orderedCards.get(4).getOrder())) {
                                            case -1:
                                                return -1;
                                            case 1:
                                                return 1;
                                            case 0:
                                                return 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            default:
                return 0;
        }
    }

    private enum Card {
        ACE("A", 0),
        KING("K", 1),
        QUEEN("Q", 2),
        JACK("J", 3),
        TEN("T", 4),
        NINE("9", 5),
        EIGHT("8", 6),
        SEVEN("7", 7),
        SIX("6", 8),
        FIVE("5", 9),
        FOUR("4", 10),
        THREE("3", 11),
        TWO("2", 12);

        private final Integer cardOrder;
        private final String card;

        Card(String card, Integer cardOrder) {
            this.cardOrder = cardOrder;
            this.card = card;
        }

        public static Card get(String card) {
            return Arrays.stream(Card.values())
                    .filter(e -> e.card.equals(card))
                    .findFirst()
                    .get();
        }

        public Integer getOrder() {
            return cardOrder;
        }
    }

    private enum CardP2 {
        ACE("A", 0),
        KING("K", 1),
        QUEEN("Q", 2),
        TEN("T", 3),
        NINE("9", 4),
        EIGHT("8", 5),
        SEVEN("7", 6),
        SIX("6", 7),
        FIVE("5", 8),
        FOUR("4", 9),
        THREE("3", 10),
        TWO("2", 11),
        JOKER("J", 12);

        private final Integer cardOrder;
        private final String card;

        CardP2(String card, Integer cardOrder) {
            this.cardOrder = cardOrder;
            this.card = card;
        }

        public static CardP2 get(String card) {
            return Arrays.stream(CardP2.values())
                    .filter(e -> e.card.equals(card))
                    .findFirst()
                    .get();
        }

        public Integer getOrder() {
            return cardOrder;
        }
    }

    private enum Rank {
        FIVE_OF_KIND,
        FOUR_OF_KIND,
        FULL_HOUSE,
        THREE_OF_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }

    private record Hand(String hand, Integer bid) {
    }

    private record ParsedHand(Rank rank, List<Card> orderedCards, Integer bid) {
    }

    private record ParsedHandP2(Rank rank, List<CardP2> orderedCards, Integer bid) {
    }
}
