import { Modal } from "react-bootstrap";
import { useEffect } from "react";

const ModalLogout = ({ setShowModal }) => {
  const handleClose = () => setShowModal(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      handleClose();
    }, 3000);
    return () => clearTimeout(timer);
  }, []);

  return (
    <>
      <Modal
        show={true}
        onHide={handleClose}
        centered
      >
        <Modal.Body className="text-center d-flex align-items-center justify-content-center">
          <p className=" text-success">
            You have been successfully logged out.
          </p>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default ModalLogout;
