package com.matjzing.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Slf4j
public class RunShellScript {

    /**
     * @param shellFile /app/script/s3/ec2_to_s3_file_sync.sh
     * @param argList
     * @return
     * @throws Exception
     */
    public static String exec(String shellFile, ArrayList<String> argList) throws Exception {

        if (null != argList && argList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (String arg : argList) {
                sb.append(arg + " ");
            }
            return RunShellScript.exec(shellFile, sb.toString());
        } else {
            throw new Exception("Argument Not Found");
        }
    }

    /**
     * @param shellFile /app/script/s3/ec2_to_s3_file_sync.sh
     * @param args      A B C
     * @return
     */
    public static String exec(String shellFile, String args) throws Exception {

        ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c", shellFile + " " + args);

        try {
            // Run script
            Process process = processBuilder.start();

            // Read output
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            process.waitFor();

            String result = output.toString();
            process.destroy();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            try {
                processBuilder.directory();
            } catch (Exception e1) {
            }
            return "SHELL RUN ERROR";
        }
    }
}
