package org.teamvan.moneyshift;

import org.teamvan.moneyshift.proto.log_msg.v1alpha1.LogMsg;

/** A sink for transport-level Rerun messages. */
public interface Transport extends AutoCloseable {
    /** Queues a message. Implementations may apply backpressure. */
    void send(LogMsg msg);

    /** Blocks until all previously sent messages have been handed off, or the timeout expires. */
    void flush(long timeoutMillis);

    @Override
    void close();
}
