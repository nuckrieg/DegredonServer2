/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.degredonserver;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author FabioAbreu
 */
public class Calculator {

    Stats p1Stats;
    Stats p2Stats;

    public void fight(Unit p1, Unit p2) {
        p1Stats = p1.getStats();
        p2Stats = p2.getStats();
        float p1DamageDealt = p1Stats.PHYS_DMG;
        final float originalDamage = p1DamageDealt;
        System.out.println("DAMAGE IS: " + p1DamageDealt);
        if (!isAttackDodged()) {
            if (isAttackCritical()) {
                p1DamageDealt *= (p1Stats.CRIT_SEV + 1.5);
                System.out.println("DAMAGE IS: " + p1DamageDealt);
            }
            if (isAttackGuarded()) {
                p1DamageDealt = getGuardEfficiency(p1DamageDealt);
                System.out.println("DAMAGE IS: " + p1DamageDealt);
            }
            p1DamageDealt = getPhysicalReduction(p1DamageDealt);
            
            System.out.println("DAMAGE IS: " + p1DamageDealt);

        }

    }
    
    public float getPhysicalReduction(float damage) {
        damage = damage * (1 - p2Stats.PHYS_RES);
        return damage;
    }

    public float getGuardEfficiency(float damage) {

        float p2Guard = p2Stats.GUARD_CHANCE;
        float p2GuardPercentage = p2Guard * 100;
        float multiplier = 1;
        float severity = 0.15f;
        float formula = 1;
        if (between((int) p2GuardPercentage, 0, 100)) {
            formula = 1;
        }
        if (between((int) p2GuardPercentage, 101, 200)) {
            formula = 0.72f;
        }
        if (between((int) p2GuardPercentage, 201, 300)) {
            formula = 0.4608f;
        }
        if (between((int) p2GuardPercentage, 301, 400)) {
            formula = 0.2949f;
        }
        if (between((int) p2GuardPercentage, 401, 500)) {
            formula = 0.1887f;
        }
        if (p2GuardPercentage > 500) {
            formula = 0.1208f;
        }
        multiplier = multiplier + (multiplier * formula);
        severity = severity * multiplier;
        damage = damage * (1 - severity);
        return damage;
    }

    public static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return i >= minValueInclusive && i <= maxValueInclusive;
    }

    public boolean isAttackDodged() {

        float p1Hit = p1Stats.HIT_CHANCE;
//        System.out.println("PLAYER 1 HIT CHANCE: " + p1Hit * 100 + "%");
        float p2Dodge = p2Stats.DODGE_CHANCE;
//        System.out.println("PLAYER 2 DODGE CHANCE: " + p2Dodge * 100 + "%");
        float effectiveHitChance = p1Hit * (1 - p2Dodge);
        float effectiveHitChanceInPercentage = effectiveHitChance * 100000;
        float randomNumber = new Random().nextInt(100000);
//        System.out.println("RANDOM NUMBER IS: "+randomNumber + " VERSUS: "+effectiveHitChanceInPercentage);
//        System.out.println("FINALIZED HIT CHANCE: " + effectiveHitChance * 100 + "%");
        boolean isDodged = randomNumber > effectiveHitChanceInPercentage;
        System.out.println("HIT? " + !isDodged + " (" + effectiveHitChance * 100 + "% HIT CHANCE)");
        if (isDodged) {
        JOptionPane.showConfirmDialog(null, "Your attack missed!", "Error!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
        }
//        System.out.println("ATTACK WAS DODGED??? "+isDodged);
    
        return isDodged;

    }

    public boolean isAttackCritical() {
        float p1Crit = p1Stats.CRIT_CHANCE;
        float p1CritPercentage = p1Crit * 100;
        //System.out.println(p1CritPercentage);
        float p1CritForRandomComparison = p1Crit * 100000;
        float randomNumber = new Random().nextInt(100000);
        boolean isCritical = randomNumber <= p1CritForRandomComparison;
        System.out.println("CRITICAL? " + isCritical + " (" + p1CritPercentage + "% CRIT CHANCE)");
        return isCritical;
    }

    public boolean isAttackGuarded() {
        float p2Guard = p2Stats.GUARD_CHANCE;
        float p2GuardPercentage = p2Guard * 100;
        //System.out.println(p2GuardPercentage);
        float p2GuardForRandomComparison = p2Guard * 100000;
        float randomNumber = new Random().nextInt(100000);
        boolean isGuarded = randomNumber <= p2GuardForRandomComparison;
        System.out.println("GUARDED? " + isGuarded + " (" + p2GuardPercentage + "% GUARD CHANCE)");

        return isGuarded;
    }

    public void testPercentages() {
        int menores = 0;
        int maiores = 0;
        for (int i = 0; i < 100000; i++) {
            Random random = new Random();
            int randomInt = random.nextInt(100000);
            if (randomInt < 50000) {
                menores++;
            } else {
                maiores++;
            }
        }
        System.out.println("MENORES: " + menores);
        System.out.println("MAIORES: " + maiores);
    }
}
