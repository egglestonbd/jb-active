/*
 * © 2018 by Intellectual Reserve, Inc. All rights reserved.
 */

package org.jbactive;

class PageWorkException extends RuntimeException {

  /**
   * Constructor for the PageWorks Exception which takes a message.
   * @param message the message to pass throw.
   */
  PageWorkException(String message) {
    super(message);
  }

  /**
   * * Constructor for the PageWork Exception which takes a message and cause.
   * @param message the message to throw.
   * @param cause the cause throwable.
   */
  PageWorkException(String message, Throwable cause) {
    super(message, cause);
  }

}
