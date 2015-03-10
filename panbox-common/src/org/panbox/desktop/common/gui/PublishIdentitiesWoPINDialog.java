/*
 * 
 *               Panbox - encryption for cloud storage 
 *      Copyright (C) 2014-2015 by Fraunhofer SIT and Sirrix AG 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Additonally, third party code may be provided with notices and open source
 * licenses from communities and third parties that govern the use of those
 * portions, and any licenses granted hereunder do not alter any rights and
 * obligations you may have under such open source licenses, however, the
 * disclaimer of warranty and limitation of liability provisions of the GPLv3 
 * will apply to all the product.
 * 
 */
package org.panbox.desktop.common.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.panbox.Settings;
import org.panbox.core.Utils;
import org.panbox.core.crypto.CryptCore;
import org.panbox.core.csp.CSPAdapterFactory;
import org.panbox.core.csp.StorageBackendType;
import org.panbox.core.exception.RandomDataGenerationException;
import org.panbox.core.identitymgmt.VCardProtector;
import org.panbox.desktop.common.PanboxClient;
import org.panbox.desktop.common.gui.addressbook.ContactListModel;
import org.panbox.desktop.common.gui.addressbook.PanboxGUIContact;
import org.panbox.desktop.common.urihandler.PanboxURICmdImportIdentity;
import org.panbox.desktop.common.utils.DesktopApi;
import org.panbox.desktop.common.vfs.backend.dropbox.CSPApiException;
import org.panbox.desktop.common.vfs.backend.dropbox.CSPIOException;
import org.panbox.desktop.common.vfs.backend.dropbox.DropboxAPIIntegration;
import org.panbox.desktop.common.vfs.backend.dropbox.DropboxAdapterFactory;

/**
 * @author palige
 * 
 *         Dialog for exporting one or more identities to a file
 */
public class PublishIdentitiesWoPINDialog extends javax.swing.JDialog {

	private static final ResourceBundle bundle = ResourceBundle.getBundle(
			"org.panbox.desktop.common.gui.Messages", Settings.getInstance()
					.getLocale());

	/**
	 * 
	 */
	private static final long serialVersionUID = -1160716976951996434L;
	private PanboxClient client;
	private List<PanboxGUIContact> contacts;
	private ContactListModel contactModel;

	/**
	 * Creates new form ExportIdentitiesDialog
	 */
	public PublishIdentitiesWoPINDialog(PanboxClient client,
			List<PanboxGUIContact> contacts) {
		super(client.getMainWindow());
		this.client = client;
		this.contacts = contacts;
		this.contactModel = new ContactListModel();
		for (PanboxGUIContact c : contacts) {
			this.contactModel.addElement(c);
		}

		initComponents();

		DocumentListener d = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkExportability();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkExportability();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkExportability();
			}
		};

	}

	/**
	 * enables export button if all prerequisites for export are being met.
	 * minimum PIN length is set to 4 characters.
	 */
	private void checkExportability() {
		publishButton.setEnabled(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        publishButton = new javax.swing.JButton();
        distributionLinkTextField = new javax.swing.JTextField();
        distributionLinkLabel = new javax.swing.JLabel();
        copyEmaiButton = new javax.swing.JButton();
        copyClipBoardButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/panbox/desktop/common/gui/Messages"); // NOI18N
        setTitle(bundle.getString("ExportIdentitiesDialog.title")); // NOI18N
        setModal(true);

        cancelButton.setText(bundle.getString("ExportIdentitiesDialog.cancelButton")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        publishButton.setText(bundle.getString("PublishIdentitiesDialog.publishButton")); // NOI18N
        publishButton.setEnabled(true);
        publishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publishButtonActionPerformed(evt);
            }
        });

        distributionLinkTextField.setEditable(false);
        distributionLinkTextField.setAutoscrolls(false);
        distributionLinkTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distributionLinkTextFieldActionPerformed(evt);
            }
        });

        distributionLinkLabel.setText(bundle.getString("PublishIdentitiesDialog.distributionLinkLabel")); // NOI18N

        copyEmaiButton.setText(bundle.getString("PublishIdentitiesDialog.copyToEmailButton")); // NOI18N
        copyEmaiButton.setEnabled(false);
        copyEmaiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyEmaiButtonActionPerformed(evt);
            }
        });

        copyClipBoardButton.setText(bundle.getString("PublishIdentitiesDialog.copyToClipBoardButton")); // NOI18N
        copyClipBoardButton.setEnabled(false);
        copyClipBoardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyClipBoardButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(distributionLinkLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(distributionLinkTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(publishButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 217, Short.MAX_VALUE)
                        .addComponent(copyClipBoardButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyEmaiButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(distributionLinkTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(distributionLinkLabel)
                    .addComponent(publishButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(copyEmaiButton)
                    .addComponent(copyClipBoardButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void copyClipBoardButtonActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_copyClipBoardButtonActionPerformed
		DesktopApi.copyToClipboard(distributionLinkTextField.getText(), true);
		this.dispose();
	}// GEN-LAST:event_copyClipBoardButtonActionPerformed

	private void copyEmaiButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_copyEmaiButtonActionPerformed
		if (Settings.getInstance().isMailtoSchemeSupported()) {
			DesktopApi
					.browse(URI
							.create("mailto:?to=&subject=" + bundle.getString("client.mailTo.importIdentitySubject") + "&body="
									+ distributionLinkTextField.getText()));
			this.dispose();
		}
	}// GEN-LAST:event_copyEmaiButtonActionPerformed

	private void distributionLinkTextFieldActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_distributionLinkTextFieldActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_distributionLinkTextFieldActionPerformed

	private DropboxAdapterFactory dbxAdapter = (DropboxAdapterFactory) CSPAdapterFactory
			.getInstance(StorageBackendType.DROPBOX);

	private void publishButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_publishButtonActionPerformed

		try {
			File vcardFile = File.createTempFile(
					"identity-export-"
							+ String.valueOf(System.currentTimeMillis()),
					".vcf");
			vcardFile.createNewFile();
			if (!vcardFile.canWrite()) {
				JOptionPane.showMessageDialog(this, bundle
						.getString("PublishIdentitiesDialog.cannotWriteFile"),
						bundle.getString("error"), JOptionPane.ERROR_MESSAGE);
			} else {
                            client.exportContacts(contacts, vcardFile);;

                            DropboxAPIIntegration dbxApi = (DropboxAPIIntegration) dbxAdapter
                                            .getAPIAdapter();
                            URI ret = dbxApi.publishFile(vcardFile);

                            distributionLinkTextField
                                            .setText(PanboxURICmdImportIdentity.getPanboxLink(ret).toString());
                            copyClipBoardButton.setEnabled(true);
                            copyEmaiButton.setEnabled(Settings.getInstance()
                                            .isMailtoSchemeSupported());
			}
		} catch (IOException e) {
			// TODO: error handling
		} catch (CSPApiException e) {
			e.printStackTrace();
		} catch (CSPIOException e) {
			e.printStackTrace();
		}

	}// GEN-LAST:event_publishButtonActionPerformed

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		this.dispose();
	}// GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton copyClipBoardButton;
    private javax.swing.JButton copyEmaiButton;
    private javax.swing.JLabel distributionLinkLabel;
    private javax.swing.JTextField distributionLinkTextField;
    private javax.swing.JButton publishButton;
    // End of variables declaration//GEN-END:variables
}
