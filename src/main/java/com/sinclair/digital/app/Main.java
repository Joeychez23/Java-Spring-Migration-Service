package com.sinclair.digital.app;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import com.sinclair.digital.app.migrate.Phases.PhaseTest0;
import com.sinclair.digital.app.migrate.Phases.PhaseTest1;
import com.sinclair.digital.app.migrate.Phases.PhaseTest2;
import com.sinclair.digital.app.migrate.Phases.PhaseTest3;
import com.sinclair.digital.app.migrate.Phases.PhaseTest4;
import com.sinclair.digital.app.migrate.Phases.PhaseTest5;
import com.sinclair.digital.app.mysql.ClearAllTables;
import com.sinclair.digital.app.mysql.ClearTables;

public class Main {

	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws IOException {

		String startDate = null;
		String endDate = null;
		String fileName = null;
		String fileNameExt = null;

		Options options = new Options();

		Option phase0a = new Option("p0", "phase0", false, "Phase 0");
		options.addOption(phase0a);

		Option phase1a = new Option("p1", "phase1", false, "Phase 1. Read content data and writes into a json file");
		options.addOption(phase1a);

		Option phase2a = new Option("p2", "phase2", false, "Phase 2. Posts Content to the MySQL DataBase");
		options.addOption(phase2a);

		Option phase3a = new Option("p3", "phase3", false, "Phase 3. Posts Sub-Tables to the MySQL DataBase");
		options.addOption(phase3a);

		Option phase4a = new Option("p4", "phase4", false, "Phase 4");
		options.addOption(phase4a);

		Option phase5a = new Option("p5", "phase5", false, "Phase 5");
		options.addOption(phase5a);

		Option clear1a = new Option("c", "clear", false, "Clear");
		options.addOption(clear1a);

		Option clearAll1a = new Option("C", "clearAll", false, "Clear all (including sql dump data)");
		options.addOption(clearAll1a);

		Option p0a = Option.builder("p0")
				.longOpt("fileNameExt")
				.argName("fileNameExt")
				.required(false)
				.desc("Post Sub Tables to MySql.")
				.build();
		options.addOption(p0a);

		Option p1a = Option.builder("p1")
				.longOpt("fileNameExt")
				.argName("fileNameExt")
				.hasArg()
				.required(false)
				.desc("Write to json file.")
				.build();
		options.addOption(p1a);

		Option p2a = Option.builder("p2")
				.longOpt("fileNameExt")
				.argName("fileNameExt")
				.hasArg()
				.required(false)
				.desc("Post to MySql.")
				.build();
		options.addOption(p2a);

		Option p3a = Option.builder("p3")
				.longOpt("fileNameExt")
				.argName("fileNameExt")
				.hasArg()
				.required(false)
				.desc("Post Sub Tables to MySql.")
				.build();
		options.addOption(p3a);

		Option p4a = Option.builder("p4")
				.longOpt("fileNameExt")
				.argName("fileNameExt")
				.hasArg()
				.required(false)
				.desc("Post Sub Tables to MySql.")
				.build();
		options.addOption(p4a);

		Option p5a = Option.builder("p5")
				.longOpt("fileNameExt")
				.argName("fileNameExt")
				.hasArg()
				.required(false)
				.desc("Post Sub Tables to MySql.")
				.build();
		options.addOption(p5a);

		Option clear = Option.builder("CLEAR")
				.required(false)
				.desc("Deletes Tables")
				.build();
		options.addOption(clear);

		Option fName = Option.builder("f")
				.longOpt("fileName")
				.argName("fileName")
				.hasArg()
				.required(false)
				.desc("Name of the output file.")
				.build();
		options.addOption(fName);

		Option sDate = Option.builder("sd")
				.longOpt("startDate")
				.argName("startdate")
				.hasArg()
				.required(false)
				.desc("Specify a starting date to transfer data, YYYY-MM-DD.")
				.build();
		options.addOption(sDate);

		Option eDate = Option.builder("ed")
				.longOpt("endDate")
				.argName("enddate")
				.hasArg()
				.required(false)
				.desc("Specify an ending date to transfer data, YYYY-MM-DD.")
				.build();
		options.addOption(eDate);

		CommandLine cmd = null;
		CommandLineParser parser = new DefaultParser();

		try {
			cmd = parser.parse(options, args);
			HelpFormatter formatter = new HelpFormatter();
			formatter.setOptionComparator(null);

			if (cmd.hasOption("startDate")) {
				startDate = cmd.getOptionValue("startDate");
				logger.info("startDate: " + startDate);
			}

			if (cmd.hasOption("endDate")) {
				endDate = cmd.getOptionValue("endDate");
				logger.info("endDate: " + endDate);
			}



			if (cmd.hasOption("-p0")) {
				logger.info("phase 0");
				new PhaseTest0();
				System.exit(0);
			}

			// Phase 1
			if (cmd.hasOption("-p1")) {

				logger.info("phase 1");
				PhaseTest1 p1 = new PhaseTest1();

				if (cmd.hasOption("-f") && startDate != null && endDate != null) { // This option reads from the content
																					// table and writes to a json file
					fileName = cmd.getOptionValue("fileName");
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					String reqExt = "";
					for (int i = 0; i < retStrArr.length; i++) {
						if (i + 1 != retStrArr.length) {
							reqExt += retStrArr[i] += "/";
						}
						if (i + 1 == retStrArr.length) {
							reqExt += fileName;
						}
					}
					logger.info(reqExt);
					p1.executeToFile(startDate, endDate, reqExt);
					logger.info("Executed");

				} else if (!cmd.hasOption("-f") && startDate != null && endDate != null) {
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");

					fileName = retStrArr[retStrArr.length - 1];

					p1.executeToFile(startDate, endDate, fileNameExt);
					logger.info("Executed");

				} else {
					logger.info("Invalid Entry");
				}

				System.exit(0);
			}

			// Phase 2
			if (cmd.hasOption("-p2")) {
				logger.info("phase 2");

				if (cmd.hasOption("-f")) {
					fileName = cmd.getOptionValue("fileName");
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					String reqExt = "";

					if (fileNameExt.substring(fileNameExt.length() - 1, fileNameExt.length()).startsWith("/")) {
						for (int i = 0; i <= retStrArr.length; i++) {
							if (i != retStrArr.length) {
								reqExt += retStrArr[i] += "/";
							}
							if (i == retStrArr.length) {
								reqExt += fileName;
							}
						}
					} else {
						for (int i = 0; i < retStrArr.length; i++) {
							if (i + 1 != retStrArr.length) {
								reqExt += retStrArr[i] += "/";
							}
							if (i + 1 == retStrArr.length) {
								reqExt += fileName;
							}
						}
					}

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					logger.info(reqExt);

					new PhaseTest2(reqExt);
				} else if (!cmd.hasOption("-f")) {
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					fileName = retStrArr[retStrArr.length - 1];

					String reqExt = fileNameExt;

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					new PhaseTest2(reqExt);
				} else {
					logger.info("Invalid Entry, No file Found");
				}

				System.exit(0);
			}

			// Phase 3
			if (cmd.hasOption("-p3")) {
				logger.info("phase 3");

				if (cmd.hasOption("-f")) {
					fileName = cmd.getOptionValue("fileName");
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					String reqExt = "";

					if (fileNameExt.substring(fileNameExt.length() - 1, fileNameExt.length()).startsWith("/")) {
						for (int i = 0; i <= retStrArr.length; i++) {
							if (i != retStrArr.length) {
								logger.info(retStrArr[i]);
								reqExt += retStrArr[i] += "/";
							}
							if (i == retStrArr.length) {
								reqExt += fileName;
							}
						}
					} else {
						for (int i = 0; i < retStrArr.length; i++) {
							if (i + 1 != retStrArr.length) {
								logger.info(retStrArr[i]);
								reqExt += retStrArr[i] += "/";
							}
							if (i + 1 == retStrArr.length) {
								reqExt += fileName;
							}
						}
					}

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					logger.info(reqExt);

					new PhaseTest3(reqExt);
				} else if (!cmd.hasOption("-f")) {
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					fileName = retStrArr[retStrArr.length - 1];

					String reqExt = fileNameExt;

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					new PhaseTest3(reqExt);
				} else {
					logger.info("Invalid Entry, No file Found");
				}

				System.exit(0);
			}

			// Phase 3
			if (cmd.hasOption("-p4")) {
				logger.info("phase 4");

				if (cmd.hasOption("-f")) {
					fileName = cmd.getOptionValue("fileName");
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					String reqExt = "";

					if (fileNameExt.substring(fileNameExt.length() - 1, fileNameExt.length()).startsWith("/")) {
						for (int i = 0; i <= retStrArr.length; i++) {
							if (i != retStrArr.length) {
								logger.info(retStrArr[i]);
								reqExt += retStrArr[i] += "/";
							}
							if (i == retStrArr.length) {
								reqExt += fileName;
							}
						}
					} else {
						for (int i = 0; i < retStrArr.length; i++) {
							if (i + 1 != retStrArr.length) {
								logger.info(retStrArr[i]);
								reqExt += retStrArr[i] += "/";
							}
							if (i + 1 == retStrArr.length) {
								reqExt += fileName;
							}
						}
					}

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					logger.info(reqExt);

					new PhaseTest4(reqExt);
				} else if (!cmd.hasOption("-f")) {
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					fileName = retStrArr[retStrArr.length - 1];

					String reqExt = fileNameExt;

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					new PhaseTest4(reqExt);
				} else {
					logger.info("Invalid Entry, No file Found");
				}

				System.exit(0);
			}

			if (cmd.hasOption("-p5")) {
				logger.info("phase 5");

				if (cmd.hasOption("-f")) {
					fileName = cmd.getOptionValue("fileName");
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					String reqExt = "";

					if (fileNameExt.substring(fileNameExt.length() - 1, fileNameExt.length()).startsWith("/")) {
						for (int i = 0; i <= retStrArr.length; i++) {
							if (i != retStrArr.length) {
								reqExt += retStrArr[i] += "/";
							}
							if (i == retStrArr.length) {
								reqExt += fileName;
							}
						}
					} else {
						for (int i = 0; i < retStrArr.length; i++) {
							if (i + 1 != retStrArr.length) {
								reqExt += retStrArr[i] += "/";
							}
							if (i + 1 == retStrArr.length) {
								reqExt += fileName;
							}
						}
					}

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					logger.info(reqExt);

					new PhaseTest5(reqExt);
				} else if (!cmd.hasOption("-f")) {
					fileNameExt = cmd.getOptionValue("fileNameExt");
					String[] retStrArr = fileNameExt.split("/");
					fileName = retStrArr[retStrArr.length - 1];

					String reqExt = fileNameExt;

					if (reqExt.startsWith("./")) {
						reqExt = reqExt.substring(2, reqExt.length());
					} else if (reqExt.startsWith("/")) {
						reqExt = reqExt.substring(1, reqExt.length());
					}

					new PhaseTest5(reqExt);
				} else {
					logger.info("Invalid Entry, No file Found");
				}

				System.exit(0);
			}

			if (cmd.hasOption("-c")) {
				new ClearTables();
			}

			if (cmd.hasOption("-C")) {
				new ClearAllTables();
			}

		
		} catch (ParseException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
