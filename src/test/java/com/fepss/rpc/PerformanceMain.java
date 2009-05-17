package com.fepss.rpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.mina.core.service.IoHandler;
import org.testng.Assert;

import com.fepss.rpc.client.RpcChannelImpl;
import com.fepss.rpc.server.impl.RpcIoHandler;
import com.fepss.rpc.server.impl.RpcServerImpl;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.Result;
import com.fepss.rpc.test.TestProto.TestService;
import com.fepss.rpc.test.TestProto.User;
import com.fepss.rpc.test.TestProto.TestService.Stub;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;

public class PerformanceMain {

	private static RpcServerImpl server;
	private static String host;
	private static int port;

	/**
	 * @param args
	 * @throws IOException 
	 * @since 0.0.2
	 */
	public static void main(String[] args) throws IOException {
	    if (args.length != 2) {
	      System.out.println("Tests the ThreadPool task.");
	      System.out
	          .println("Usage: java ThreadPoolTest numTasks numThreads");
	      System.out.println("  numTasks - integer: number of task to run.");
	      System.out.println("  numThreads - integer: number of threads "
	          + "in the thread pool.");
	      return;
	    }
		host = "localhost";
		port = 8081;
		
		
	    int numTasks = Integer.parseInt(args[0]);
	    int numThreads = Integer.parseInt(args[1]);

	    // create the thread pool
	    ThreadPool threadPool = new ThreadPool(numThreads);

	    // run example tasks
	    for (int i = 0; i < numTasks; i++) {
	      threadPool.runTask(createTask(i));
	    }

	    // close the pool and wait for all tasks to finish.
	    threadPool.join();
	    System.out.println("finish to execute All tasks!");
	}

	  /**
	   * Creates a simple Runnable that prints an ID, waits 500 milliseconds, then
	   * prints the ID again.
	   */
	  private static Runnable createTask(final int taskID) {
	    return new Runnable() {
	      public void run() {
	        System.out.println("Task " + taskID + ": start");


		// create client to call rpc
		RpcChannelImpl channel = new RpcChannelImpl(host, port);
		RpcController controller = channel.newRpcController();
		Stub service = TestService.newStub(channel);
		// request data
		String reqdata = "Request Data";
		User request = User.newBuilder().setUserName(reqdata).build();

		// response callback
		RpcCallback<Result> done = new RpcCallback<Result>() {
			@Override
			public void run(Result result) {
				Assert.assertEquals(result.getResult(), "get userRequest Data");
				Assert.assertTrue(result.getSuccess());

			}
		};
		// execute remote method
		service.testMethod(controller, request, done);
		Assert.assertFalse(controller.failed());
		Assert.assertEquals(controller.errorText(), null);
	        System.out.println("Task " + taskID + ": end");
	      }
	    };
	  }
}
	/**
	 * A thread pool is a group of a limited number of threads that are used to
	 * execute tasks.
	 */

	class ThreadPool extends ThreadGroup {

	  private boolean isAlive;

	  private LinkedList taskQueue;

	  private int threadID;

	  private static int threadPoolID;

	  /**
	   * Creates a new ThreadPool.
	   * 
	   * @param numThreads
	   *            The number of threads in the pool.
	   */
	  public ThreadPool(int numThreads) {
	    super("ThreadPool-" + (threadPoolID++));
	    setDaemon(true);

	    isAlive = true;

	    taskQueue = new LinkedList();
	    for (int i = 0; i < numThreads; i++) {
	      new PooledThread().start();
	    }
	  }

	  /**
	   * Requests a new task to run. This method returns immediately, and the task
	   * executes on the next available idle thread in this ThreadPool.
	   * <p>
	   * Tasks start execution in the order they are received.
	   * 
	   * @param task
	   *            The task to run. If null, no action is taken.
	   * @throws IllegalStateException
	   *             if this ThreadPool is already closed.
	   */
	  public synchronized void runTask(Runnable task) {
	    if (!isAlive) {
	      throw new IllegalStateException();
	    }
	    if (task != null) {
	      taskQueue.add(task);
	      notify();
	    }

	  }

	  protected synchronized Runnable getTask() throws InterruptedException {
	    while (taskQueue.size() == 0) {
	      if (!isAlive) {
	        return null;
	      }
	      wait();
	    }
	    return (Runnable) taskQueue.removeFirst();
	  }

	  /**
	   * Closes this ThreadPool and returns immediately. All threads are stopped,
	   * and any waiting tasks are not executed. Once a ThreadPool is closed, no
	   * more tasks can be run on this ThreadPool.
	   */
	  public synchronized void close() {
	    if (isAlive) {
	      isAlive = false;
	      taskQueue.clear();
	      interrupt();
	    }
	  }

	  /**
	   * Closes this ThreadPool and waits for all running threads to finish. Any
	   * waiting tasks are executed.
	   */
	  public void join() {
	    // notify all waiting threads that this ThreadPool is no
	    // longer alive
	    synchronized (this) {
	      isAlive = false;
	      notifyAll();
	    }

	    // wait for all threads to finish
	    Thread[] threads = new Thread[activeCount()];
	    int count = enumerate(threads);
	    for (int i = 0; i < count; i++) {
	      try {
	        threads[i].join();
	      } catch (InterruptedException ex) {
	      }
	    }
	  }

	  /**
	   * A PooledThread is a Thread in a ThreadPool group, designed to run tasks
	   * (Runnables).
	   */
	  private class PooledThread extends Thread {

	    public PooledThread() {
	      super(ThreadPool.this, "PooledThread-" + (threadID++));
	    }

	    public void run() {
	      while (!isInterrupted()) {

	        // get a task to run
	        Runnable task = null;
	        try {
	          task = getTask();
	        } catch (InterruptedException ex) {
	        }

	        // if getTask() returned null or was interrupted,
	        // close this thread by returning.
	        if (task == null) {
	          return;
	        }

	        // run the task, and eat any exceptions it throws
	        try {
	          task.run();
	        } catch (Throwable t) {
	          uncaughtException(this, t);
	        }
	      }
	    }
	  }
	}
	           
	         
	  