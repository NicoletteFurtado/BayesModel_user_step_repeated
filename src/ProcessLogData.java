import java.util.ArrayList;
import java.util.Arrays;

public class ProcessLogData {

	private static int UPDATED_lIST_SIZE = 0;

	public void updateLogData(StudentLogData student, InitMaps initMaps) {
		removeExtraAttempts(student);
		repeatAttempts(student, initMaps);
	}

	// remove all attempts except the first
	public void removeExtraAttempts(StudentLogData student) {
		ArrayList<String> actionListNew = new ArrayList<String>();
		ArrayList<String> verificationListNew = new ArrayList<String>();
		ArrayList<String> sentenceListNew = new ArrayList<String>();
		ArrayList<Integer> userStepNew = new ArrayList<Integer>();
		ArrayList<String> inputDataNew = new ArrayList<String>();
		// ArrayList<String> userStepRepeatedNew = new ArrayList<String>();

		String prevSentence = "";
		Integer prevUserStep = 0;
		for (int i = 0; i < student.getSentenceList().size(); i++) {
			if (!prevSentence.equals(student.getSentenceList().get(i)) || prevUserStep != student.getUserStep().get(i)) {
				// if not equal to previous then add
				verificationListNew.add(student.getVerificationList().get(i));
				actionListNew.add(student.getActionList().get(i));
				sentenceListNew.add(student.getSentenceList().get(i));
				userStepNew.add(student.getUserStep().get(i));
				inputDataNew.add(student.getInputData().get(i));

				System.out.println("added step " + student.getUserStep().get(i) + "of sentence "
						+ student.getSentenceList().get(i));
			}
			prevSentence = student.getSentenceList().get(i);
			prevUserStep = student.getUserStep().get(i);
		}
		student.setVerificationList(verificationListNew);
		student.setActionList(actionListNew);
		student.setSentenceList(sentenceListNew);
		student.setUserStep(userStepNew);
		student.setInputData(inputDataNew);
		UPDATED_lIST_SIZE = sentenceListNew.size();
		System.out.println("first attempt=" + Arrays.toString(student.getUserStep().toArray()));
		System.out.println("first attempt=" + student.getVerificationList().size());
	}

	// repeat number of attempts (skill-1) times, since one is already present
	public void repeatAttempts(StudentLogData student, InitMaps initMaps) {
		int j = 0;
		ArrayList<String> actionListNew = new ArrayList<String>();
		ArrayList<String> verificationListNew = new ArrayList<String>();
		ArrayList<String> sentenceListNew = new ArrayList<String>();
		ArrayList<Integer> userStepNew = new ArrayList<Integer>();
		ArrayList<String> inputDataNew = new ArrayList<String>();

		for (int i = 0; i < UPDATED_lIST_SIZE; i++) {
			// get the skills for this sentence
			System.out.println("i=" + i);
			ArrayList<String> wordsInSentence = initMaps.getSentenceToWords().get(
					AnalysisUtil.convertStringToKey(student.getSentenceList().get(i)));
			System.out.println(AnalysisUtil.convertStringToKey(student.getSentenceList().get(i)));
			System.out.println("/////// " + wordsInSentence);
			if (!wordsInSentence.contains(Constants.DEFAULT_WORD)) {
				// get all data for the first attempt
				String currSentence = student.getSentenceList().get(i);
				String currVerification = student.getVerificationList().get(i);
				String currAction = student.getActionList().get(i);
				Integer currUserStep = student.getUserStep().get(i);
				String currInputData = student.getInputData().get(i);
				// you have the first attempt
				// add the data at point i wordsInSentence number of times
				j = 0;
				while (j < (wordsInSentence.size())) {
					// for (int j = 0; j < (wordsInSentence.size() - 1); j++) {
					System.out.println("j=" + j);
					// add at i th position, j times
					sentenceListNew.add(currSentence);
					verificationListNew.add(currVerification);
					actionListNew.add(currAction);
					userStepNew.add(currUserStep);
					inputDataNew.add(i, currInputData);
					// inputDataNew.add(i, currInputData);
					System.out.println("repeated step " + currUserStep + "of sentence " + currSentence + " for word "
							+ wordsInSentence.get(j));
					j++;
				}
			}
		}
		student.setVerificationList(verificationListNew);
		student.setActionList(actionListNew);
		student.setSentenceList(sentenceListNew);
		student.setUserStep(userStepNew);
		student.setInputData(inputDataNew);
		System.out.println("final attempt=" + Arrays.toString(student.getVerificationList().toArray()));
		System.out.println("final attempt=" + student.getVerificationList().size());
	}
}
