function ModalLogout({ showModal, showOverlay }) {
  return (
    <>
      {showOverlay && (
        <div className="position-fixed top-0 left-0 w-100 h-100 bg-dark opacity-75 z-999"></div>
      )}
      <div
        className={`modal fade ${showModal ? "show" : ""}`}
        style={{ display: showModal ? "block" : "none" }}
        tabIndex="-1"
        role="dialog"
        aria-labelledby="exampleModalCenterTitle"
        aria-hidden="true"
      >
        <div
          className="modal-dialog modal-dialog-centered"
          role="document"
        >
          <div className="modal-content">
            <div className="modal-header">
              <h2
                className="modal-title text-success"
                id="exampleModalCenterTitle"
              >
                Logout Successful!
              </h2>
            </div>
            <div className="modal-body">
              You have been successfully logged out. Thank you for using our
              platform.
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default ModalLogout;
