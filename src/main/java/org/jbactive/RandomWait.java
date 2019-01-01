/*
 * © 2018 by Intellectual Reserve, Inc. All rights reserved.
 */

package org.jbactive;

import java.util.Random;

class RandomWait {

  private RandomWait() throws InstantiationException {
    throw new InstantiationException("Cannot instantiate this class directly. ");
  }

  private static Random r = new Random();

  static long getRandomInteger(){
    int min = 500;
    int max = 5000;
    return r.nextInt((max - min) + 1) + (long) min;
  }

}
