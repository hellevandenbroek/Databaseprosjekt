SELECT Pulse.DatagiverID, Pulse.Timestamp, Pulse.BPM, 
Datagiver.Firstname, Datagiver.Lastname FROM Pulse
INNER JOIN Datagiver ON Pulse.DatagiverID = Datagiver.DatagiverID;
