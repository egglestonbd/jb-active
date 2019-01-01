/*
 * © 2018 by Intellectual Reserve, Inc. All rights reserved.
 */

package org.jbactive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Work {

  private static final Logger LOGGER = LoggerFactory.getLogger(Work.class);


  public static void main(String[] args) throws InterruptedException {

    LOGGER.info("Launching Application.");

    for(int i = 0; i < 10; i++)
    {
      long random = RandomWait.getRandomInteger();
      Thread.sleep(random);
      LOGGER.info("Attempting: {}, Waiting MS: {}", i, random);
    }


  }


}
